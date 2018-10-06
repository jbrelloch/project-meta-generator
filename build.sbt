lazy val root = (project in file(".")).
  settings(
    name := "project-meta-generator",
    version := "0.1",
    organization := "ch.brello",
    scalaVersion := "2.12.7",
    sbtPlugin := true,
    sbtVersion := "0.13.11"
  )