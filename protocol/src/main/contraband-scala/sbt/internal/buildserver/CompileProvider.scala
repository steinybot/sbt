/**
 * This code is generated using [[http://www.scala-sbt.org/contraband/ sbt-contraband]].
 */

// DO NOT EDIT MANUALLY
package sbt.internal.buildserver
/** The languages the server supports compilation via method buildTarget/compile. */
final class CompileProvider private (
  val languageIds: Vector[String]) extends Serializable {
  
  
  
  override def equals(o: Any): Boolean = o match {
    case x: CompileProvider => (this.languageIds == x.languageIds)
    case _ => false
  }
  override def hashCode: Int = {
    37 * (37 * (17 + "sbt.internal.buildserver.CompileProvider".##) + languageIds.##)
  }
  override def toString: String = {
    "CompileProvider(" + languageIds + ")"
  }
  private[this] def copy(languageIds: Vector[String] = languageIds): CompileProvider = {
    new CompileProvider(languageIds)
  }
  def withLanguageIds(languageIds: Vector[String]): CompileProvider = {
    copy(languageIds = languageIds)
  }
}
object CompileProvider {
  
  def apply(languageIds: Vector[String]): CompileProvider = new CompileProvider(languageIds)
}
