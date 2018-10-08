scalaVersion := "2.10.7"

lazy val projectMetaGenerator = (project in file(".")).
  settings(
    name := "project-meta-generator",
    version := "0.1",
    organization := "ch.brello",
    sbtPlugin := true,
    sbtVersion := "0.13.7",
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.module" % "jackson-modules-base" % "2.9.2",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.2"
    )
  )

//initialCommands in console := "import ch.brello.plugins.projectMeta._"