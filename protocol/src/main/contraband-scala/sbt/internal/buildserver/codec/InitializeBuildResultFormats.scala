/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver.codec
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait InitializeBuildResultFormats { self: sbt.internal.buildserver.codec.BuildServerCapabilitiesFormats with sbt.internal.util.codec.JValueFormats with sjsonnew.BasicJsonProtocol =>
implicit lazy val InitializeBuildResultFormat: JsonFormat[sbt.internal.buildserver.InitializeBuildResult] = new JsonFormat[sbt.internal.buildserver.InitializeBuildResult] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.internal.buildserver.InitializeBuildResult = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val displayName = unbuilder.readField[String]("displayName")
      val version = unbuilder.readField[String]("version")
      val bspVersion = unbuilder.readField[String]("bspVersion")
      val capabilities = unbuilder.readField[sbt.internal.buildserver.BuildServerCapabilities]("capabilities")
      val data = unbuilder.readField[Option[sjsonnew.shaded.scalajson.ast.unsafe.JValue]]("data")
      unbuilder.endObject()
      sbt.internal.buildserver.InitializeBuildResult(displayName, version, bspVersion, capabilities, data)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.internal.buildserver.InitializeBuildResult, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("displayName", obj.displayName)
    builder.addField("version", obj.version)
    builder.addField("bspVersion", obj.bspVersion)
    builder.addField("capabilities", obj.capabilities)
    builder.addField("data", obj.data)
    builder.endObject()
  }
}
}
