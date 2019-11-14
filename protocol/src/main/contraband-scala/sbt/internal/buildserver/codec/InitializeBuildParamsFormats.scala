/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver.codec
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait InitializeBuildParamsFormats { self: sbt.internal.buildserver.codec.BuildClientCapabilitiesFormats with sbt.internal.util.codec.JValueFormats with sjsonnew.BasicJsonProtocol =>
implicit lazy val InitializeBuildParamsFormat: JsonFormat[sbt.internal.buildserver.InitializeBuildParams] = new JsonFormat[sbt.internal.buildserver.InitializeBuildParams] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.internal.buildserver.InitializeBuildParams = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val displayName = unbuilder.readField[String]("displayName")
      val version = unbuilder.readField[String]("version")
      val bspVersion = unbuilder.readField[String]("bspVersion")
      val rootUri = unbuilder.readField[String]("rootUri")
      val capabilities = unbuilder.readField[sbt.internal.buildserver.BuildClientCapabilities]("capabilities")
      val data = unbuilder.readField[Option[sjsonnew.shaded.scalajson.ast.unsafe.JValue]]("data")
      unbuilder.endObject()
      sbt.internal.buildserver.InitializeBuildParams(displayName, version, bspVersion, rootUri, capabilities, data)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.internal.buildserver.InitializeBuildParams, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("displayName", obj.displayName)
    builder.addField("version", obj.version)
    builder.addField("bspVersion", obj.bspVersion)
    builder.addField("rootUri", obj.rootUri)
    builder.addField("capabilities", obj.capabilities)
    builder.addField("data", obj.data)
    builder.endObject()
  }
}
}
