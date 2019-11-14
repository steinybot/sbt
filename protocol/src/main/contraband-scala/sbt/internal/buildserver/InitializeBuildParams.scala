/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver
/**
 * The first request from the client to the server.
 * @param displayName Name of the client
 * @param version The version of the client
 * @param bspVersion The BSP version that the client speaks
 * @param rootUri The rootUri of the workspace
 * @param capabilities The capabilities of the client
 * @param data Additional metadata about the client
 */
final class InitializeBuildParams private (
  val displayName: String,
  val version: String,
  val bspVersion: String,
  val rootUri: String,
  val capabilities: sbt.internal.buildserver.BuildClientCapabilities,
  val data: Option[sjsonnew.shaded.scalajson.ast.unsafe.JValue]) extends Serializable {
  
  
  
  override def equals(o: Any): Boolean = o match {
    case x: InitializeBuildParams => (this.displayName == x.displayName) && (this.version == x.version) && (this.bspVersion == x.bspVersion) && (this.rootUri == x.rootUri) && (this.capabilities == x.capabilities) && (this.data == x.data)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (37 * (37 * (37 * (17 + "sbt.internal.buildserver.InitializeBuildParams".##) + displayName.##) + version.##) + bspVersion.##) + rootUri.##) + capabilities.##) + data.##)
  }
  override def toString: String = {
    "InitializeBuildParams(" + displayName + ", " + version + ", " + bspVersion + ", " + rootUri + ", " + capabilities + ", " + data + ")"
  }
  private[this] def copy(displayName: String = displayName, version: String = version, bspVersion: String = bspVersion, rootUri: String = rootUri, capabilities: sbt.internal.buildserver.BuildClientCapabilities = capabilities, data: Option[sjsonnew.shaded.scalajson.ast.unsafe.JValue] = data): InitializeBuildParams = {
    new InitializeBuildParams(displayName, version, bspVersion, rootUri, capabilities, data)
  }
  def withDisplayName(displayName: String): InitializeBuildParams = {
    copy(displayName = displayName)
  }
  def withVersion(version: String): InitializeBuildParams = {
    copy(version = version)
  }
  def withBspVersion(bspVersion: String): InitializeBuildParams = {
    copy(bspVersion = bspVersion)
  }
  def withRootUri(rootUri: String): InitializeBuildParams = {
    copy(rootUri = rootUri)
  }
  def withCapabilities(capabilities: sbt.internal.buildserver.BuildClientCapabilities): InitializeBuildParams = {
    copy(capabilities = capabilities)
  }
  def withData(data: Option[sjsonnew.shaded.scalajson.ast.unsafe.JValue]): InitializeBuildParams = {
    copy(data = data)
  }
  def withData(data: sjsonnew.shaded.scalajson.ast.unsafe.JValue): InitializeBuildParams = {
    copy(data = Option(data))
  }
}
object InitializeBuildParams {
  
  def apply(displayName: String, version: String, bspVersion: String, rootUri: String, capabilities: sbt.internal.buildserver.BuildClientCapabilities, data: Option[sjsonnew.shaded.scalajson.ast.unsafe.JValue]): InitializeBuildParams = new InitializeBuildParams(displayName, version, bspVersion, rootUri, capabilities, data)
  def apply(displayName: String, version: String, bspVersion: String, rootUri: String, capabilities: sbt.internal.buildserver.BuildClientCapabilities, data: sjsonnew.shaded.scalajson.ast.unsafe.JValue): InitializeBuildParams = new InitializeBuildParams(displayName, version, bspVersion, rootUri, capabilities, Option(data))
}
