package ch.brello.plugins.projectMeta.models

import ch.brello.plugins.projectMeta.models.dependencies.Dependency
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

case class ProjectMeta(
    micro: Project,
    owner: Owner,
    dependencies: List[Dependency]
)

object ProjectMeta {

  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)

  def toJson(projectMeta: ProjectMeta): String = {
    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(projectMeta)
  }
}
