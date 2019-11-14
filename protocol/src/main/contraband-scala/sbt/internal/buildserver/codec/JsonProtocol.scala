/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver.codec
trait JsonProtocol extends sjsonnew.BasicJsonProtocol
  with sbt.internal.buildserver.codec.BuildTargetIdentifierFormats
  with sbt.internal.buildserver.codec.BuildTargetCapabilitiesFormats
  with sbt.internal.util.codec.JValueFormats
  with sbt.internal.buildserver.codec.BuildTargetFormats
  with sbt.internal.buildserver.codec.BuildClientCapabilitiesFormats
  with sbt.internal.buildserver.codec.InitializeBuildParamsFormats
  with sbt.internal.buildserver.codec.CompileProviderFormats
  with sbt.internal.buildserver.codec.TestProviderFormats
  with sbt.internal.buildserver.codec.RunProviderFormats
  with sbt.internal.buildserver.codec.BuildServerCapabilitiesFormats
  with sbt.internal.buildserver.codec.InitializeBuildResultFormats
  with sbt.internal.buildserver.codec.WorkspaceBuildTargetsResultFormats
object JsonProtocol extends JsonProtocol