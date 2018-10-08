package ch.brello.plugins.projectMeta.models

import com.fasterxml.jackson.annotation.JsonCreator

case class Project @JsonCreator() (name: String,
    prettyName: String,
    marathonName: String,
    technologies: List[String],
    propertyIdentifier: Option[String]) {

  def this(name: String,
      prettyName: String,
      marathonName: String,
      technologies: List[String]) = this(name, prettyName, marathonName, technologies, None)
}

object Project {
  def apply(name: String,
      prettyName: String,
      marathonName: String,
      technologies: List[String]): Project = new Project(name, prettyName, marathonName, technologies, None)
}
