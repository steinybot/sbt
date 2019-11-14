/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver.codec
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait WorkspaceBuildTargetsParamsFormats { self: sjsonnew.BasicJsonProtocol =>
implicit lazy val WorkspaceBuildTargetsParamsFormat: JsonFormat[sbt.internal.buildserver.WorkspaceBuildTargetsParams] = new JsonFormat[sbt.internal.buildserver.WorkspaceBuildTargetsParams] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.internal.buildserver.WorkspaceBuildTargetsParams = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      
      unbuilder.endObject()
      sbt.internal.buildserver.WorkspaceBuildTargetsParams()
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.internal.buildserver.WorkspaceBuildTargetsParams, builder: Builder[J]): Unit = {
    builder.beginObject()
    
    builder.endObject()
  }
}
}
