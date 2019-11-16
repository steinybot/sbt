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
      val buildTargets = for {
        (project, ref) <- extracted.structure.allProjectPairs
        configuration <- project.configurations
        // TODO: Support non-Scala targets.
        buildTarget <- scalaBuildTargets(extracted, project, ref, configuration)
      } yield buildTarget
      state.respondEvent(WorkspaceBuildTargetsResult(buildTargets.toVector))
      state
  }

  private def scalaBuildTargets(
      extracted: Extracted,
      project: ResolvedProject,
      ref: ProjectRef,
      configuration: Configuration
  ): Seq[BuildTarget] = {
    getScalaVersions(extracted, ref, configuration) match {
      case Some(scalaVersions) =>
        scalaVersions.map { scalaVersion =>
          scalaBuildTarget(extracted, project, ref, configuration, scalaVersion)
        }
      case None => Seq(scalaBuildTarget(extracted, project, ref, configuration))
    }
  }

  private def getScalaVersions(
      extracted: Extracted,
      ref: ProjectRef,
      configuration: Configuration
  ): Option[Seq[String]] = {
    return extracted
      .getOpt(crossScalaVersions.in(ref, configuration))
      .orElse(extracted.getOpt(Keys.scalaVersion.in(ref, configuration)).map(Seq(_)))
  }

  private def scalaBuildTarget(
      extracted: Extracted,
      project: ResolvedProject,
      ref: ProjectRef,
      configuration: Configuration,
      scalaVersion: String
  ): BuildTarget = {
    buildTarget(
      extracted,
      project,
      ref,
      configuration,
      // TODO: Figure out what the URI is that we want to use.
      ref.build + configuration.id + "/" + scalaVersion,
      project.id + " " + configuration.name + " (" + scalaVersion + ")"
    )
  }

  private def scalaBuildTarget(
      extracted: Extracted,
      project: ResolvedProject,
      ref: ProjectRef,
      configuration: Configuration
  ): BuildTarget = {
    buildTarget(
      extracted,
      project,
      ref,
      configuration,
      ref.build + configuration.id,
      project.id + " " + configuration.name
    )
  }

  private def buildTarget(
      extracted: Extracted,
      project: ResolvedProject,
      ref: ProjectRef,
      configuration: Configuration,
      uri: String,
      displayName: String
  ): BuildTarget = {
    BuildTarget(
      BuildTargetIdentifier(uri),
      Some(displayName),
      Some(project.base.getPath()),
      Vector.empty,
      buildTargetCapabilities(extracted, ref, configuration),
      // TODO: Support other languages.
      Vector("scala"),
      Vector.empty,
      None,
      None
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
}
