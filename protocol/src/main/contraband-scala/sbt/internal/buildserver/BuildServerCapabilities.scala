/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver
/**
 * @param compileProvider The languages the server supports compilation via method buildTarget/compile.
 * @param testProvider The languages the server supports test execution via method buildTarget/test.
 * @param runProvider The languages the server supports run via method buildTarget/run.
 * @param inverseSourcesProvider The server can provide a list of targets that contain a
                                 single text document via the method buildTarget/inverseSources.
 * @param dependencySourcesProvider The server provides sources for library dependencies
                                    via method buildTarget/dependencySources.
 * @param resourcesProvider The server provides all the resource dependencies
                            via method buildTarget/resources.
 * @param buildTargetChangedProvider The server sends notifications to the client on build
                                     target change events via buildTarget/didChange.
 */
final class BuildServerCapabilities private (
  val compileProvider: Option[sbt.internal.buildserver.CompileProvider],
  val testProvider: Option[sbt.internal.buildserver.TestProvider],
  val runProvider: Option[sbt.internal.buildserver.RunProvider],
  val inverseSourcesProvider: Option[Boolean],
  val dependencySourcesProvider: Option[Boolean],
  val resourcesProvider: Option[Boolean],
  val buildTargetChangedProvider: Option[Boolean]) extends Serializable {
  
  
  
  override def equals(o: Any): Boolean = o match {
    case x: BuildServerCapabilities => (this.compileProvider == x.compileProvider) && (this.testProvider == x.testProvider) && (this.runProvider == x.runProvider) && (this.inverseSourcesProvider == x.inverseSourcesProvider) && (this.dependencySourcesProvider == x.dependencySourcesProvider) && (this.resourcesProvider == x.resourcesProvider) && (this.buildTargetChangedProvider == x.buildTargetChangedProvider)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (37 * (37 * (37 * (37 * (37 * (37 * (17 + "sbt.internal.buildserver.BuildServerCapabilities".##) + compileProvider.##) + testProvider.##) + runProvider.##) + inverseSourcesProvider.##) + dependencySourcesProvider.##) + resourcesProvider.##) + buildTargetChangedProvider.##)
  }
  override def toString: String = {
    "BuildServerCapabilities(" + compileProvider + ", " + testProvider + ", " + runProvider + ", " + inverseSourcesProvider + ", " + dependencySourcesProvider + ", " + resourcesProvider + ", " + buildTargetChangedProvider + ")"
  }
  private[this] def copy(compileProvider: Option[sbt.internal.buildserver.CompileProvider] = compileProvider, testProvider: Option[sbt.internal.buildserver.TestProvider] = testProvider, runProvider: Option[sbt.internal.buildserver.RunProvider] = runProvider, inverseSourcesProvider: Option[Boolean] = inverseSourcesProvider, dependencySourcesProvider: Option[Boolean] = dependencySourcesProvider, resourcesProvider: Option[Boolean] = resourcesProvider, buildTargetChangedProvider: Option[Boolean] = buildTargetChangedProvider): BuildServerCapabilities = {
    new BuildServerCapabilities(compileProvider, testProvider, runProvider, inverseSourcesProvider, dependencySourcesProvider, resourcesProvider, buildTargetChangedProvider)
  }
  def withCompileProvider(compileProvider: Option[sbt.internal.buildserver.CompileProvider]): BuildServerCapabilities = {
    copy(compileProvider = compileProvider)
  }
  def withCompileProvider(compileProvider: sbt.internal.buildserver.CompileProvider): BuildServerCapabilities = {
    copy(compileProvider = Option(compileProvider))
  }
  def withTestProvider(testProvider: Option[sbt.internal.buildserver.TestProvider]): BuildServerCapabilities = {
    copy(testProvider = testProvider)
  }
  def withTestProvider(testProvider: sbt.internal.buildserver.TestProvider): BuildServerCapabilities = {
    copy(testProvider = Option(testProvider))
  }
  def withRunProvider(runProvider: Option[sbt.internal.buildserver.RunProvider]): BuildServerCapabilities = {
    copy(runProvider = runProvider)
  }
  def withRunProvider(runProvider: sbt.internal.buildserver.RunProvider): BuildServerCapabilities = {
    copy(runProvider = Option(runProvider))
  }
  def withInverseSourcesProvider(inverseSourcesProvider: Option[Boolean]): BuildServerCapabilities = {
    copy(inverseSourcesProvider = inverseSourcesProvider)
  }
  def withInverseSourcesProvider(inverseSourcesProvider: Boolean): BuildServerCapabilities = {
    copy(inverseSourcesProvider = Option(inverseSourcesProvider))
  }
  def withDependencySourcesProvider(dependencySourcesProvider: Option[Boolean]): BuildServerCapabilities = {
    copy(dependencySourcesProvider = dependencySourcesProvider)
  }
  def withDependencySourcesProvider(dependencySourcesProvider: Boolean): BuildServerCapabilities = {
    copy(dependencySourcesProvider = Option(dependencySourcesProvider))
  }
  def withResourcesProvider(resourcesProvider: Option[Boolean]): BuildServerCapabilities = {
    copy(resourcesProvider = resourcesProvider)
  }
  def withResourcesProvider(resourcesProvider: Boolean): BuildServerCapabilities = {
    copy(resourcesProvider = Option(resourcesProvider))
  }
  def withBuildTargetChangedProvider(buildTargetChangedProvider: Option[Boolean]): BuildServerCapabilities = {
    copy(buildTargetChangedProvider = buildTargetChangedProvider)
  }
  def withBuildTargetChangedProvider(buildTargetChangedProvider: Boolean): BuildServerCapabilities = {
    copy(buildTargetChangedProvider = Option(buildTargetChangedProvider))
  }
}
object BuildServerCapabilities {
  
  def apply(compileProvider: Option[sbt.internal.buildserver.CompileProvider], testProvider: Option[sbt.internal.buildserver.TestProvider], runProvider: Option[sbt.internal.buildserver.RunProvider], inverseSourcesProvider: Option[Boolean], dependencySourcesProvider: Option[Boolean], resourcesProvider: Option[Boolean], buildTargetChangedProvider: Option[Boolean]): BuildServerCapabilities = new BuildServerCapabilities(compileProvider, testProvider, runProvider, inverseSourcesProvider, dependencySourcesProvider, resourcesProvider, buildTargetChangedProvider)
  def apply(compileProvider: sbt.internal.buildserver.CompileProvider, testProvider: sbt.internal.buildserver.TestProvider, runProvider: sbt.internal.buildserver.RunProvider, inverseSourcesProvider: Boolean, dependencySourcesProvider: Boolean, resourcesProvider: Boolean, buildTargetChangedProvider: Boolean): BuildServerCapabilities = new BuildServerCapabilities(Option(compileProvider), Option(testProvider), Option(runProvider), Option(inverseSourcesProvider), Option(dependencySourcesProvider), Option(resourcesProvider), Option(buildTargetChangedProvider))
}
