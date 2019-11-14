/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver.codec
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait TestProviderFormats { self: sjsonnew.BasicJsonProtocol =>
implicit lazy val TestProviderFormat: JsonFormat[sbt.internal.buildserver.TestProvider] = new JsonFormat[sbt.internal.buildserver.TestProvider] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.internal.buildserver.TestProvider = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val languageIds = unbuilder.readField[Vector[String]]("languageIds")
      unbuilder.endObject()
      sbt.internal.buildserver.TestProvider(languageIds)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.internal.buildserver.TestProvider, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("languageIds", obj.languageIds)
    builder.endObject()
  }
}
}
