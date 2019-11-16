/*
 * sbt
 * Copyright 2011 - 2019, Lightbend, Inc.
 * Copyright 2008 - 2010, Mark Harrah
 * Licensed under Apache License 2.0 (see LICENSE)
 */

package sbt
package plugins

import Def._
import Keys._
import StateServerOps._
import sbt.internal.buildserver._
import sbt.internal.buildserver.codec.JsonProtocol._
import sbt.librarymanagement.Configuration
import sjsonnew.support.scalajson.unsafe.Converter

/**
 * An experimental plugin that adds commands required to implement the Build Server Protocol.
 */
object BuildServerProtocolPlugin extends AutoPlugin {
  override def requires = CorePlugin
  override def trigger = allRequirements

  override lazy val globalSettings: Seq[Setting[_]] =
    Seq(
      commands += workspaceBuildTargetsCommand
    )

  private def workspaceBuildTargetsCommand: Command = Command.command("bspWorkspaceBuildTargets") {
    state: State =>
      val extracted = state.extract
      state.s.configuration
      val targets = for {
        (project, ref) <- extracted.structure.allProjectPairs
        configuration <- project.configurations
        // TODO: Support non-Scala targets.
        buildTarget <- buildTargets(extracted, project, ref, configuration)
      } yield buildTarget
      state.respondEvent(WorkspaceBuildTargetsResult(targets.toVector))
      state
  }

  private def buildTargets(
      extracted: Extracted,
      project: ResolvedProject,
      ref: ProjectRef,
      configuration: Configuration
  ): Seq[BuildTarget] = {
    scalaBuildTargets(extracted, project, ref, configuration) // ++
    //sbtBuildTargets(extracted)
  }

  private def scalaBuildTargets(
      extracted: Extracted,
      project: ResolvedProject,
      ref: ProjectRef,
      configuration: Configuration
  ): Seq[BuildTarget] = {
    getScalaVersions(extracted, ref, configuration).map { scalaVersion =>
      scalaBuildTarget(extracted, project, ref, configuration, scalaVersion)
    }
  }

  private def getScalaVersions(
      extracted: Extracted,
      ref: ProjectRef,
      configuration: Configuration
  ): Seq[String] = {
    return extracted
      .getOpt(crossScalaVersions.in(ref, configuration))
      .getOrElse(extracted.getOpt(Keys.scalaVersion.in(ref, configuration)).toSeq)
  }

  private def scalaBuildTarget(
      extracted: Extracted,
      project: ResolvedProject,
      ref: ProjectRef,
      configuration: Configuration,
      scalaVersion: String
  ): BuildTarget = {
    BuildTarget(
      // TODO: Figure out what the URI is that we want to use.
      BuildTargetIdentifier(ref.build + configuration.id + "/" + scalaVersion),
      project.id + " " + configuration.name + " (" + scalaVersion + ")",
      project.base.getPath(),
      Vector.empty,
      buildTargetCapabilities(extracted, ref, configuration),
      Vector("scala"),
      // TODO: What do we put for dependencies?
      Vector.empty,
      // TODO: sbt meta builds.
      // "sbt",
      // Converter.toJsonUnsafe(
      //   SbtBuildTarget(
      //     extracted.get(sbtVersion),
      //   )
      // )
      // TODO: Test for the type (maybe use scalaInstance?).
      "scala",
      Converter.toJsonUnsafe(
        ScalaBuildTarget(
          extracted.get(scalaOrganization.in(ref, configuration)),
          scalaVersion,
          // TODO: How do we handle this for cross builds?
          extracted.get(sbtBinaryVersion.in(ref, configuration)),
          // TODO: Use correct platform.
          sbt.internal.buildserver.ScalaPlatform.JVM,
          scalaJars(extracted, ref, configuration)
        )
      )
    )
  }

  private def buildTargetCapabilities(
      extracted: Extracted,
      ref: ProjectRef,
      configuration: Configuration
  ): BuildTargetCapabilities = {
    val runKey = run.in(ref, configuration)
    BuildTargetCapabilities(
      extracted.getOpt(Keys.compile.in(ref, configuration)).nonEmpty,
      extracted.getOpt(test.in(ref, configuration)).nonEmpty,
      extracted.structure.data.get(runKey.scope, run.key).nonEmpty
    )
  }

  private def scalaJars(
      extracted: Extracted,
      ref: ProjectRef,
      configuration: Configuration
  ): Vector[String] = {
    extracted
      .get(appConfiguration.in(ref, configuration))
      .provider
      .scalaProvider
      .jars
      .filter { jar =>
        val name = jar.getName
        name == "scala-library.jar" || name == "scala-compiler.jar" || name == "scala-reflet.jar"
      }
      .map(_.getName)
      .toVector
  }
}
