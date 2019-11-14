/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver.codec
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait WorkspaceBuildTargetsResultFormats { self: sbt.internal.buildserver.codec.BuildTargetFormats with sjsonnew.BasicJsonProtocol =>
implicit lazy val WorkspaceBuildTargetsResultFormat: JsonFormat[sbt.internal.buildserver.WorkspaceBuildTargetsResult] = new JsonFormat[sbt.internal.buildserver.WorkspaceBuildTargetsResult] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.internal.buildserver.WorkspaceBuildTargetsResult = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val targets = unbuilder.readField[Vector[sbt.internal.buildserver.BuildTarget]]("targets")
      unbuilder.endObject()
      sbt.internal.buildserver.WorkspaceBuildTargetsResult(targets)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.internal.buildserver.WorkspaceBuildTargetsResult, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("targets", obj.targets)
    builder.endObject()
  }
}
}
