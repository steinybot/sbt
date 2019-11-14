/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver
/**
 * The languages the server supports run via method buildTarget/run.
 * https://microsoft.github.io/language-server-protocol/specifications/specification-3-14/#textdocumentitem
 */
final class RunProvider private (
  val languageIds: Vector[String]) extends Serializable {
  
  
  
  override def equals(o: Any): Boolean = o match {
    case x: RunProvider => (this.languageIds == x.languageIds)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (17 + "sbt.internal.buildserver.RunProvider".##) + languageIds.##)
  }
  override def toString: String = {
    "RunProvider(" + languageIds + ")"
  }
  private[this] def copy(languageIds: Vector[String] = languageIds): RunProvider = {
    new RunProvider(languageIds)
  }
  def withLanguageIds(languageIds: Vector[String]): RunProvider = {
    copy(languageIds = languageIds)
  }
}
object RunProvider {
  
  def apply(languageIds: Vector[String]): RunProvider = new RunProvider(languageIds)
}
