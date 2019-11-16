/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver.codec
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait SbtBuildTargetFormats { self: sbt.internal.buildserver.codec.ScalaBuildTargetFormats with sbt.internal.buildserver.codec.BuildTargetIdentifierFormats with sjsonnew.BasicJsonProtocol =>
implicit lazy val SbtBuildTargetFormat: JsonFormat[sbt.internal.buildserver.SbtBuildTarget] = new JsonFormat[sbt.internal.buildserver.SbtBuildTarget] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.internal.buildserver.SbtBuildTarget = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val sbtVersion = unbuilder.readField[String]("sbtVersion")
      val autoImports = unbuilder.readField[Vector[String]]("autoImports")
      val scalaBuildTarget = unbuilder.readField[sbt.internal.buildserver.ScalaBuildTarget]("scalaBuildTarget")
      val parent = unbuilder.readField[Option[sbt.internal.buildserver.BuildTargetIdentifier]]("parent")
      val children = unbuilder.readField[Vector[sbt.internal.buildserver.BuildTargetIdentifier]]("children")
      unbuilder.endObject()
      sbt.internal.buildserver.SbtBuildTarget(sbtVersion, autoImports, scalaBuildTarget, parent, children)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.internal.buildserver.SbtBuildTarget, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("sbtVersion", obj.sbtVersion)
    builder.addField("autoImports", obj.autoImports)
    builder.addField("scalaBuildTarget", obj.scalaBuildTarget)
    builder.addField("parent", obj.parent)
    builder.addField("children", obj.children)
    builder.endObject()
  }
}
}
