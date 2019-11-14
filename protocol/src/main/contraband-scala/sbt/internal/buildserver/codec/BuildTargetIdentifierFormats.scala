/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver.codec
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait BuildTargetIdentifierFormats { self: sjsonnew.BasicJsonProtocol =>
implicit lazy val BuildTargetIdentifierFormat: JsonFormat[sbt.internal.buildserver.BuildTargetIdentifier] = new JsonFormat[sbt.internal.buildserver.BuildTargetIdentifier] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.internal.buildserver.BuildTargetIdentifier = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val uri = unbuilder.readField[String]("uri")
      unbuilder.endObject()
      sbt.internal.buildserver.BuildTargetIdentifier(uri)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.internal.buildserver.BuildTargetIdentifier, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("uri", obj.uri)
    builder.endObject()
  }
}
}
