/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver.codec
import _root_.sjsonnew.{ Unbuilder, Builder, JsonFormat, deserializationError }
trait BuildServerCapabilitiesFormats { self: sbt.internal.buildserver.codec.CompileProviderFormats with sbt.internal.buildserver.codec.TestProviderFormats with sbt.internal.buildserver.codec.RunProviderFormats with sjsonnew.BasicJsonProtocol =>
implicit lazy val BuildServerCapabilitiesFormat: JsonFormat[sbt.internal.buildserver.BuildServerCapabilities] = new JsonFormat[sbt.internal.buildserver.BuildServerCapabilities] {
  override def read[J](jsOpt: Option[J], unbuilder: Unbuilder[J]): sbt.internal.buildserver.BuildServerCapabilities = {
    jsOpt match {
      case Some(js) =>
      unbuilder.beginObject(js)
      val compileProvider = unbuilder.readField[Option[sbt.internal.buildserver.CompileProvider]]("compileProvider")
      val testProvider = unbuilder.readField[Option[sbt.internal.buildserver.TestProvider]]("testProvider")
      val runProvider = unbuilder.readField[Option[sbt.internal.buildserver.RunProvider]]("runProvider")
      val inverseSourcesProvider = unbuilder.readField[Option[Boolean]]("inverseSourcesProvider")
      val dependencySourcesProvider = unbuilder.readField[Option[Boolean]]("dependencySourcesProvider")
      val resourcesProvider = unbuilder.readField[Option[Boolean]]("resourcesProvider")
      val buildTargetChangedProvider = unbuilder.readField[Option[Boolean]]("buildTargetChangedProvider")
      unbuilder.endObject()
      sbt.internal.buildserver.BuildServerCapabilities(compileProvider, testProvider, runProvider, inverseSourcesProvider, dependencySourcesProvider, resourcesProvider, buildTargetChangedProvider)
      case None =>
      deserializationError("Expected JsObject but found None")
    }
  }
  override def write[J](obj: sbt.internal.buildserver.BuildServerCapabilities, builder: Builder[J]): Unit = {
    builder.beginObject()
    builder.addField("compileProvider", obj.compileProvider)
    builder.addField("testProvider", obj.testProvider)
    builder.addField("runProvider", obj.runProvider)
    builder.addField("inverseSourcesProvider", obj.inverseSourcesProvider)
    builder.addField("dependencySourcesProvider", obj.dependencySourcesProvider)
    builder.addField("resourcesProvider", obj.resourcesProvider)
    builder.addField("buildTargetChangedProvider", obj.buildTargetChangedProvider)
    builder.endObject()
  }
}
}
