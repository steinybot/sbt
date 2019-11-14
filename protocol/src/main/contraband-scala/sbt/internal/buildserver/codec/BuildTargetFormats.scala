/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver.codec
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait BuildTargetFormats { self: sbt.internal.buildserver.codec.BuildTargetIdentifierFormats with sbt.internal.buildserver.codec.BuildTargetCapabilitiesFormats with sbt.internal.util.codec.JValueFormats with sjsonnew.BasicJsonProtocol =>
implicit lazy val BuildTargetFormat: JsonFormat[sbt.internal.buildserver.BuildTarget] = new JsonFormat[sbt.internal.buildserver.BuildTarget] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.internal.buildserver.BuildTarget = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val id = unbuilder.readField[sbt.internal.buildserver.BuildTargetIdentifier]("id")
      val displayName = unbuilder.readField[Option[String]]("displayName")
      val baseDirectory = unbuilder.readField[Option[String]]("baseDirectory")
      val tags = unbuilder.readField[Vector[String]]("tags")
      val capabilities = unbuilder.readField[sbt.internal.buildserver.BuildTargetCapabilities]("capabilities")
      val languageIds = unbuilder.readField[Vector[String]]("languageIds")
      val dependencies = unbuilder.readField[Vector[sbt.internal.buildserver.BuildTargetIdentifier]]("dependencies")
      val dataKind = unbuilder.readField[Option[String]]("dataKind")
      val data = unbuilder.readField[Option[sjsonnew.shaded.scalajson.ast.unsafe.JValue]]("data")
      unbuilder.endObject()
      sbt.internal.buildserver.BuildTarget(id, displayName, baseDirectory, tags, capabilities, languageIds, dependencies, dataKind, data)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.internal.buildserver.BuildTarget, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("id", obj.id)
    builder.addField("displayName", obj.displayName)
    builder.addField("baseDirectory", obj.baseDirectory)
    builder.addField("tags", obj.tags)
    builder.addField("capabilities", obj.capabilities)
    builder.addField("languageIds", obj.languageIds)
    builder.addField("dependencies", obj.dependencies)
    builder.addField("dataKind", obj.dataKind)
    builder.addField("data", obj.data)
    builder.endObject()
  }
}
}
