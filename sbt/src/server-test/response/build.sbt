import sbt.internal.server.{ ServerHandler, ServerIntent }

ThisBuild / scalaVersion := "2.12.10"

Global / serverLog / logLevel := Level.Debug
// custom handler
Global / serverHandlers += ServerHandler({ callback =>
  import callback._
  import sjsonnew.BasicJsonProtocol._
  import sbt.internal.protocol.JsonRpcRequestMessage
  ServerIntent(
    {
      case r: JsonRpcRequestMessage if r.method == "foo/rootClasspath" =>
        appendExec(Exec("fooClasspath", Some(r.id), Some(CommandSource(callback.name))))
        ()
    },
    PartialFunction.empty
  )
})

lazy val fooClasspath = taskKey[Unit]("")
lazy val root = (project in file("."))
  .settings(
    name := "response",
    fooClasspath := {
      val s = state.value
      val cp = (Compile / fullClasspath).value
      s.respondEvent(cp.map(_.data))
    }
  )
