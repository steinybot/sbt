/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver
/** The languages the server supports test execution via method buildTarget/test. */
final class TestProvider private (
  val languageIds: Vector[String]) extends Serializable {
  
  
  
  override def equals(o: Any): Boolean = o match {
    case x: TestProvider => (this.languageIds == x.languageIds)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (17 + "sbt.internal.buildserver.TestProvider".##) + languageIds.##)
  }
  override def toString: String = {
    "TestProvider(" + languageIds + ")"
  }
  private[this] def copy(languageIds: Vector[String] = languageIds): TestProvider = {
    new TestProvider(languageIds)
  }
  def withLanguageIds(languageIds: Vector[String]): TestProvider = {
    copy(languageIds = languageIds)
  }
}
object TestProvider {
  
  def apply(languageIds: Vector[String]): TestProvider = new TestProvider(languageIds)
}
