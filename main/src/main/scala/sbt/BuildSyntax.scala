/*
 * sbt
 * Copyright 2011 - 2018, Lightbend, Inc.
 * Copyright 2008 - 2010, Mark Harrah
 * Licensed under Apache License 2.0 (see LICENSE)
 */

package sbt

import sbt.internal.DslEntry
import sbt.librarymanagement.Configuration
import sjsonnew.JsonFormat

private[sbt] trait BuildSyntax {
  import scala.language.experimental.macros
  def settingKey[T](description: String): SettingKey[T] = macro std.KeyMacro.settingKeyImpl[T]
  def taskKey[T](description: String): TaskKey[T] = macro std.KeyMacro.taskKeyImpl[T]
  def inputKey[T](description: String): InputKey[T] = macro std.KeyMacro.inputKeyImpl[T]

  def enablePlugins(ps: AutoPlugin*): DslEntry = DslEntry.DslEnablePlugins(ps)
  def disablePlugins(ps: AutoPlugin*): DslEntry = DslEntry.DslDisablePlugins(ps)
  def configs(cs: Configuration*): DslEntry = DslEntry.DslConfigs(cs)
  def dependsOn(deps: ClasspathDep[ProjectReference]*): DslEntry = DslEntry.DslDependsOn(deps)
  // avoid conflict with `sbt.Keys.aggregate`
  def aggregateProjects(refs: ProjectReference*): DslEntry = DslEntry.DslAggregate(refs)

  implicit def sbtStateToStateServerOps(s: State): StateServerOps =
    new StateServerOps.StateServerOpsImpl(s)
}
private[sbt] object BuildSyntax extends BuildSyntax

trait StateServerOps extends Any {
  def extract: Extracted
  def respondEvent[A: JsonFormat](event: A): Unit
  def respondError(code: Long, message: String): Unit
  def notifyEvent[A: JsonFormat](method: String, params: A): Unit
}

object StateServerOps {
  lazy val exchange = StandardMain.exchange

  implicit class StateServerOpsImpl(val s: State) extends AnyVal with StateServerOps {
    def extract: Extracted = Project.extract(s)
    def respondEvent[A: JsonFormat](event: A): Unit = {
      exchange.respondEvent(event, s.currentCommand.flatMap(_.execId), s.source)
    }
    def respondError(code: Long, message: String): Unit = ()
    def notifyEvent[A: JsonFormat](method: String, params: A): Unit = ()
  }
}
