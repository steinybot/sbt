/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver.codec
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait BuildTargetIdentiferFormats { self: sjsonnew.BasicJsonProtocol =>
implicit lazy val BuildTargetIdentiferFormat: JsonFormat[sbt.internal.buildserver.BuildTargetIdentifer] = new JsonFormat[sbt.internal.buildserver.BuildTargetIdentifer] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.internal.buildserver.BuildTargetIdentifer = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val uri = unbuilder.readField[String]("uri")
      unbuilder.endObject()
      sbt.internal.buildserver.BuildTargetIdentifer(uri)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.internal.buildserver.BuildTargetIdentifer, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("uri", obj.uri)
    builder.endObject()
  }
}
}
