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
    s0: State =>
      s0.respondEvent(
        WorkspaceBuildTargetsResult(
          Vector.apply(
            BuildTarget(
              BuildTargetIdentifier("foo-uri"),
              None,
              None,
              Vector.empty,
              BuildTargetCapabilities(true, true, true),
              Vector.empty,
              Vector.empty,
              None,
              None
            )
          )
        )
      )
      s0
  }
}
