/*
 * sbt
 * Copyright 2011 - 2017, Lightbend, Inc.
 * Copyright 2008 - 2010, Mark Harrah
 * Licensed under BSD-3-Clause license (see LICENSE)
 */

package sbt

import java.net.URI

import org.scalacheck.Gen._
import org.scalacheck.Prop._
import org.scalacheck._
import sbt.Def.{ScopedKey, displayFull, displayMasked}
import sbt.internal.TestBuild._
import sbt.internal.util.AttributeKey
import sbt.internal.util.complete._
import sbt.internal.{Resolve, TestBuild}
import sbt.librarymanagement.Configuration

/**
 * Tests that the scoped key parser in Act can correctly parse a ScopedKey converted by Def.show*Key.
 * This includes properly resolving omitted components.
 */
object ParseKey extends Properties("Key parser test") {
  final val MaxKeys = 5
  final val MaxScopedKeys = 100

  implicit val gstructure = genStructure

  property("An explicitly specified axis is always parsed to that explicit value") =
    forAll(structureDefinedKey) { (skm: StructureKeyMask) =>
      import skm.{key, structure, mask => mask0}

      val hasZeroConfig = key.scope.config == Zero
      val mask = if (hasZeroConfig) mask0.copy(project = true) else mask0
      val expected = resolve(structure, key, mask)
      // Note that this explicitly displays the configuration axis set to Zero.
      // This is to disambiguate `proj/Zero/name`, which could render potentially
      // as `Zero/name`, but could be interpretted as `Zero/Zero/name`.
      val s = displayMasked(key, mask, hasZeroConfig)
      ("Key: " + displayPedantic(key)) |:
        parseExpected(structure, s, expected, mask)
    }

  property("An unspecified project axis resolves to the current project") =
    forAll(structureDefinedKey) { (skm: StructureKeyMask) =>
      import skm.{key, structure}

      val mask = skm.mask.copy(project = false)
      val string = displayMasked(key, mask)
      // skip when config axis is set to Zero
      val hasZeroConfig = key.scope.config == Zero

      ("Key: " + displayPedantic(key)) |:
        ("Mask: " + mask) |:
        ("Current: " + structure.current) |:
        parse(structure, string) {
        case Left(err)                  => false
        case Right(sk) if hasZeroConfig => true
        case Right(sk)                  => sk.scope.project == Select(structure.current)
      }
    }

  property("An unspecified task axis resolves to Zero") = forAll(structureDefinedKey) {
    (skm: StructureKeyMask) =>
      import skm.{key, structure}
      val mask = skm.mask.copy(task = false)
      val string = displayMasked(key, mask)

      ("Key: " + displayPedantic(key)) |:
        ("Mask: " + mask) |:
        parse(structure, string) {
        case Left(err) => false
        case Right(sk) => sk.scope.task == Zero
      }
  }

  property(
    "An unspecified configuration axis resolves to the first configuration directly defining the key or else Zero") =
    forAll(structureDefinedKey) { (skm: StructureKeyMask) =>
      import skm.{key, structure}
      val mask = ScopeMask(config = false)
      val string = displayMasked(key, mask)
      val resolvedConfig = Resolve.resolveConfig(structure.extra, key.key, mask)(key.scope).config

      ("Key: " + displayPedantic(key)) |:
        ("Mask: " + mask) |:
        ("Expected configuration: " + resolvedConfig.map(_.name)) |:
        parse(structure, string) {
        case Right(sk) => (sk.scope.config == resolvedConfig) || (sk.scope == Scope.GlobalScope)
        case Left(err) => false
      }
    }

  def displayPedantic(scoped: ScopedKey[_]): String =
    Scope.displayPedantic(scoped.scope, scoped.key.label)

  lazy val structureDefinedKey: Gen[StructureKeyMask] = structureKeyMask { s =>
    for (scope <- TestBuild.scope(s.env); key <- oneOf(s.allAttributeKeys.toSeq))
      yield ScopedKey(scope, key)
  }
  def structureKeyMask(genKey: Structure => Gen[ScopedKey[_]])(
      implicit maskGen: Gen[ScopeMask],
      structureGen: Gen[Structure]): Gen[StructureKeyMask] =
    for {
      mask <- maskGen
      structure <- structureGen
      key <- genKey(structure)
      skm = StructureKeyMask(structure, key, mask)
      if configExistsInIndex(skm)
    } yield skm
  final case class StructureKeyMask(structure: Structure, key: ScopedKey[_], mask: ScopeMask)

  private def configExistsInIndex(skm: StructureKeyMask): Boolean = {
    import skm._
    val resolvedKey = resolve(structure, key, mask)
    val proj = resolvedKey.scope.project.toOption
    val maybeResolvedProj = proj.collect {
      case ref: ResolvedReference => ref
    }
    val checkName = for {
      configKey <- resolvedKey.scope.config.toOption
    } yield {
      val configID = Scope.display(configKey)
      // This only works for known configurations or those that were guessed correctly.
      val name = structure.keyIndex.fromConfigIdent(maybeResolvedProj)(configID)
      name == configKey.name
    }
    checkName.getOrElse(true)
  }

  def resolve(structure: Structure, key: ScopedKey[_], mask: ScopeMask): ScopedKey[_] =
    ScopedKey(Resolve(structure.extra, Select(structure.current), key.key, mask)(key.scope),
              key.key)

  def parseExpected(structure: Structure,
                    s: String,
                    expected: ScopedKey[_],
                    mask: ScopeMask): Prop =
    ("Expected: " + displayFull(expected)) |:
      ("Mask: " + mask) |:
      parse(structure, s) {
      case Left(err) => false
      case Right(sk) =>
        (s"${sk}.key == ${expected}.key: ${sk.key == expected.key}") |:
          (s"${sk.scope} == ${expected.scope}: ${Scope.equal(sk.scope, expected.scope, mask)}") |:
          Project.equal(sk, expected, mask)
    }

  def parse(structure: Structure, s: String)(f: Either[String, ScopedKey[_]] => Prop): Prop = {
    val parser = makeParser(structure)
    val parsed = DefaultParsers.result(parser, s).left.map(_().toString)
    val showParsed = parsed.right.map(displayFull)
    ("Key string: '" + s + "'") |:
      ("Parsed: " + showParsed) |:
      ("Structure: " + structure) |:
      f(parsed)
  }

  // Here we're shadowing the in-scope implicit called `mkEnv` for this method
  // so that it will use the passed-in `Gen` rather than the one imported
  // from TestBuild.
  def genStructure(implicit mkEnv: Gen[Env]): Gen[Structure] =
    structureGenF { (scopes: Seq[Scope], env: Env, current: ProjectRef) =>
      val settings = structureSettings(scopes, env)
      TestBuild.structure(env, settings, current)
    }

  def structureSettings(scopes: Seq[Scope], env: Env): Seq[Def.Setting[String]] = {
    for {
      scope <- scopes
      t <- env.tasks
    } yield Def.setting(ScopedKey(scope, t.key), Def.value(""))
  }

  // Here we're shadowing the in-scope implicit called `mkEnv` for this method
  // so that it will use the passed-in `Gen` rather than the one imported
  // from TestBuild.
  def structureGenF(f: (Seq[Scope], Env, ProjectRef) => Structure)(
      implicit mkEnv: Gen[Env]): Gen[Structure] =
    structureGen((s, e, p) => Gen.const(f(s, e, p)))
  // Here we're shadowing the in-scope implicit called `mkEnv` for this method
  // so that it will use the passed-in `Gen` rather than the one imported
  // from TestBuild.
  def structureGen(f: (Seq[Scope], Env, ProjectRef) => Gen[Structure])(
      implicit mkEnv: Gen[Env]): Gen[Structure] =
    for {
      env <- mkEnv
      loadFactor <- choose(0.0, 1.0)
      scopes <- pickN(loadFactor, env.allFullScopes)
      current <- oneOf(env.allProjects.unzip._1)
      structure <- f(scopes, env, current)
    } yield structure

  // pickN is a function that randomly picks load % items from the from sequence.
  // The rest of the tests expect at least one item, so I changed it to return 1 in case of 0.
  def pickN[T](load: Double, from: Seq[T]): Gen[Seq[T]] =
    pick(Math.max((load * from.size).toInt, 1), from)

  implicit val shrinkStructureKeyMask: Shrink[StructureKeyMask] = Shrink { skm =>
    Shrink
      .shrink(skm.structure)
      .map(s => skm.copy(structure = s))
      .flatMap(fixKey)
  }

  def fixKey(skm: StructureKeyMask): Stream[StructureKeyMask] = {
    for {
      scope <- fixScope(skm)
      attributeKey <- fixAttributeKey(skm)
    } yield skm.copy(key = ScopedKey(scope, attributeKey))
  }

  def fixScope(skm: StructureKeyMask): Stream[Scope] = {
    def validScope(scope: Scope) = scope match {
      case Scope(Select(BuildRef(build)), _, _, _) if !validBuild(build) => false
      case Scope(Select(ProjectRef(build, project)), _, _, _) if !validProject(build, project) =>
        false
      case Scope(Select(ProjectRef(build, project)), Select(ConfigKey(config)), _, _)
          if !validConfig(build, project, config) =>
        false
      case Scope(_, Select(ConfigKey(config)), _, _) if !configExists(config) =>
        false
      case Scope(_, _, Select(task), _) => validTask(task)
      case _                            => true
    }
    def validBuild(build: URI) = skm.structure.env.buildMap.contains(build)
    def validProject(build: URI, project: String) = {
      skm.structure.env.buildMap
        .get(build)
        .exists(_.projectMap.contains(project))
    }
    def validConfig(build: URI, project: String, config: String) = {
      skm.structure.env.buildMap
        .get(build)
        .toSeq
        .flatMap(_.projectMap.get(project))
        .flatMap(_.configurations.map(_.name))
        .contains(config)
    }
    def configExists(config: String) = {
      val configs = for {
        build <- skm.structure.env.builds
        project <- build.projects
        config <- project.configurations
      } yield config.name
      configs.contains(config)
    }
    def validTask(task: AttributeKey[_]) = skm.structure.env.taskMap.contains(task)
    if (validScope(skm.key.scope)) {
      Stream(skm.key.scope)
    } else {
      // We could return all scopes here but we want to explore the other paths first since there
      // is a greater chance of a successful shrink. If necessary these could be appended to the end.
      Stream.empty
    }
  }

  def fixAttributeKey(skm: StructureKeyMask): Stream[AttributeKey[_]] = {
    if (skm.structure.allAttributeKeys.contains(skm.key.key)) {
      Stream(skm.key.key)
    } else {
      // Likewise here, we should try other paths before trying different attribute keys.
      Stream.empty
    }
  }

  implicit val shrinkStructure: Shrink[Structure] = Shrink { s =>
    Shrink.shrink(s.env).flatMap { env =>
      val scopes = s.data.scopes intersect env.allFullScopes.toSet
      val settings = structureSettings(scopes.toSeq, env)
      if (settings.nonEmpty) {
        val currents = env.allProjects.find {
          case (ref, _) => ref == s.current
        } match {
          case Some((current, _)) => Stream(current)
          case None               => env.allProjects.map(_._1).toStream
        }
        currents.map(structure(env, settings, _))
      } else {
        Stream.empty
      }
    }
  }

  implicit val shrinkEnv: Shrink[Env] = Shrink { env =>
    val shrunkBuilds = Shrink
      .shrink(env.builds)
      .filter(_.nonEmpty)
      .map(b => env.copy(builds = b))
      .map(fixProjectRefs)
      .map(fixConfigurations)
    val shrunkTasks = shrinkTasks(env.tasks)
      .map(t => env.copy(tasks = t))
    shrunkBuilds ++ shrunkTasks
  }

  private def fixProjectRefs(env: Env): Env = {
    def fixBuild(build: Build): Build = {
      build.copy(projects = build.projects.map(fixProject))
    }
    def fixProject(project: Proj): Proj = {
      project.copy(delegates = project.delegates.filter(delegateExists))
    }
    def delegateExists(delegate: ProjectRef): Boolean = {
      env.buildMap
        .get(delegate.build)
        .flatMap(_.projectMap.get(delegate.project))
        .nonEmpty
    }
    env.copy(builds = env.builds.map(fixBuild))
  }

  private def fixConfigurations(env: Env): Env = {
    val configs = env.allProjects.map {
      case (_, proj) => proj -> proj.configurations.toSet
    }.toMap

    def fixBuild(build: Build): Build = {
      build.copy(projects = build.projects.map(fixProject(build.uri)))
    }
    def fixProject(buildURI: URI)(project: Proj): Proj = {
      val projConfigs = configs(project)
      project.copy(configurations = project.configurations.map(fixConfig(projConfigs)))
    }
    def fixConfig(projConfigs: Set[Configuration])(config: Configuration): Configuration = {
      import config.{name => configName, _}
      val extendsConfigs = config.extendsConfigs.filter(projConfigs.contains)
      Configuration.of(id, configName, description, isPublic, extendsConfigs, transitive)
    }
    env.copy(builds = env.builds.map(fixBuild))
  }

  implicit val shrinkBuild: Shrink[Build] = Shrink { build =>
    Shrink
      .shrink(build.projects)
      .filter(_.nonEmpty)
      .map(p => build.copy(projects = p))
  // Could also shrink the URI here but that requires updating all the references.
  }

  implicit val shrinkProject: Shrink[Proj] = Shrink { project =>
    val shrunkDelegates = Shrink
      .shrink(project.delegates)
      .map(d => project.copy(delegates = d))
    val shrunkConfigs = Shrink
      .shrink(project.configurations)
      .map(c => project.copy(configurations = c))
    val shrunkID = shrinkID(project.id)
      .map(id => project.copy(id = id))
    shrunkDelegates ++ shrunkConfigs ++ shrunkID
  }

  implicit val shrinkDelegate: Shrink[ProjectRef] = Shrink { delegate =>
    val shrunkBuild = Shrink
      .shrink(delegate.build)
      .map(b => delegate.copy(build = b))
    val shrunkProject = Shrink
      .shrink(delegate.project)
      .map(p => delegate.copy(project = p))
    shrunkBuild ++ shrunkProject
  }

  implicit val shrinkConfiguration: Shrink[Configuration] = Shrink { configuration =>
    import configuration.{name => configName, _}
    val shrunkExtends = Shrink
      .shrink(configuration.extendsConfigs)
      .map(configuration.withExtendsConfigs)
    val shrunkID = Shrink.shrink(id.tail).map { tail =>
      Configuration
        .of(id.head + tail, configName, description, isPublic, extendsConfigs, transitive)
    }
    shrunkExtends ++ shrunkID
  }

  val shrinkStringLength: Shrink[String] = Shrink { s =>
    // Only change the string length don't change the characters.
    implicit val shrinkChar: Shrink[Char] = Shrink.shrinkAny
    Shrink.shrinkContainer[List, Char].shrink(s.toList).map(_.mkString)
  }

  def shrinkID(id: String): Stream[String] = {
    Shrink.shrink(id).filter(DefaultParsers.validID)
  }

  def shrinkTasks(tasks: Vector[Taskk]): Stream[Vector[Taskk]] = {
    Shrink.shrink(tasks)
  }

  implicit val shrinkTask: Shrink[Taskk] = Shrink { task =>
    Shrink.shrink((task.delegates, task.key)).map {
      case (delegates, key) => Taskk(key, delegates)
    }
  }
}
