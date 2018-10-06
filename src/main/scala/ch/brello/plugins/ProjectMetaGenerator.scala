package ch.brello.plugins

import sbt._

object ProjectMetaGenerator extends AutoPlugin  {
  object autoImport {
    val projectDefinitionFileName = settingKey[String]("The file with the project definition.")
    val buildProjectMeta = inputKey[Unit]("Writes project meta definition")
  }

  import autoImport._

  override def projectSettings: Seq[Setting[_]] = Seq(
    projectMeta
  )

  def projectMeta: Setting[_] = buildProjectMeta := {
    println(s"Loading ${projectDefinitionFileName.value}")
  }
}
