/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver.codec
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait BuildTargetCapabilitiesFormats { self: sjsonnew.BasicJsonProtocol =>
implicit lazy val BuildTargetCapabilitiesFormat: JsonFormat[sbt.internal.buildserver.BuildTargetCapabilities] = new JsonFormat[sbt.internal.buildserver.BuildTargetCapabilities] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.internal.buildserver.BuildTargetCapabilities = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val canCompile = unbuilder.readField[Boolean]("canCompile")
      val canTest = unbuilder.readField[Boolean]("canTest")
      val canRun = unbuilder.readField[Boolean]("canRun")
      unbuilder.endObject()
      sbt.internal.buildserver.BuildTargetCapabilities(canCompile, canTest, canRun)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.internal.buildserver.BuildTargetCapabilities, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("canCompile", obj.canCompile)
    builder.addField("canTest", obj.canTest)
    builder.addField("canRun", obj.canRun)
    builder.endObject()
  }
}
}
