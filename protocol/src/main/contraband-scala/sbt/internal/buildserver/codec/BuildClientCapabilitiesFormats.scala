/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver.codec
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait BuildClientCapabilitiesFormats { self: sjsonnew.BasicJsonProtocol =>
implicit lazy val BuildClientCapabilitiesFormat: JsonFormat[sbt.internal.buildserver.BuildClientCapabilities] = new JsonFormat[sbt.internal.buildserver.BuildClientCapabilities] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.internal.buildserver.BuildClientCapabilities = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val languageIds = unbuilder.readField[Vector[String]]("languageIds")
      unbuilder.endObject()
      sbt.internal.buildserver.BuildClientCapabilities(languageIds)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.internal.buildserver.BuildClientCapabilities, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("languageIds", obj.languageIds)
    builder.endObject()
  }
}
}
