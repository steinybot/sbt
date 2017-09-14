import sbt._, Keys._

import StatusPlugin.autoImport._
import com.typesafe.sbt.site.SitePlugin.autoImport._
import com.typesafe.sbt.site.SiteScaladocPlugin
import SiteScaladocPlugin.autoImport._
import com.typesafe.sbt.sbtghpages.GhpagesPlugin
import GhpagesPlugin.autoImport._
import com.typesafe.sbt.SbtGit, SbtGit.{ git, GitKeys }
import Sxr.{ sxr, SxrConf }

object DocsPlugin extends AutoPlugin {
  override def requires = GhpagesPlugin && SiteScaladocPlugin

  val siteExcludes = Set(".buildinfo", "objects.inv")
  def siteInclude(f: File) = !siteExcludes.contains(f.getName)

  override def projectSettings: Seq[Setting[_]] = Def settings (
    siteSubdirName in SiteScaladoc := "api",
    siteIncludeSxr("sxr"),
    ghPagesSettings
  )

  def ghPagesSettings = Def settings (
    git.remoteRepo := "git@github.com:sbt/sbt.github.com.git",
    localRepoDirectory,
    ghpagesSynchLocal := synchLocalImpl.value,
    ghpagesBranch := "master"
  )

  def localRepoDirectory = ghpagesRepository := {
    // distinguish between building to update the site or not so that CI jobs
    //  that don't commit+publish don't leave uncommitted changes in the working directory
    val status = if (isSnapshot.value) "snapshot" else "public"
    Path.userHome / ".sbt" / "ghpages" / status / organization.value / name.value
  }

  def siteIncludeSxr(prefix: String) = Def settings (
    mappings in sxr := Path.allSubpaths(sxr.value).toSeq,
    siteSubdirName in SxrConf := prefix,
    addMappingsToSiteDir(mappings in sxr, siteSubdirName in SxrConf)
  )

  def synchLocalImpl = Def task {
    val repo = ghpagesUpdatedRepository.value
    val versioned = repo / version.value
    IO.delete(versioned / "sxr")
    IO.delete(versioned / "api")
    val mappings = ghpagesPrivateMappings.value
    val toCopy = for ((file, target) <- mappings if siteInclude(file))
      yield (file, versioned / target)
    IO.copy(toCopy)
    repo
  }
}
