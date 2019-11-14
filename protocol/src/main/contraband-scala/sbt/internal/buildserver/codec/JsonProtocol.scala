/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver.codec
trait JsonProtocol extends sjsonnew.BasicJsonProtocol
  with sbt.internal.buildserver.codec.BuildClientCapabilitiesFormats
  with sbt.internal.util.codec.JValueFormats
  with sbt.internal.buildserver.codec.InitializeBuildParamsFormats
  with sbt.internal.buildserver.codec.CompileProviderFormats
  with sbt.internal.buildserver.codec.TestProviderFormats
  with sbt.internal.buildserver.codec.RunProviderFormats
  with sbt.internal.buildserver.codec.BuildServerCapabilitiesFormats
  with sbt.internal.buildserver.codec.InitializeBuildResultFormats
object JsonProtocol extends JsonProtocol