package ch.brello.plugins.projectMeta.strategies.write

import ch.brello.plugins.projectMeta.models.ProjectMeta

case class JsonPrintlnWriteStrategy() extends WriteStrategy {
  override def generateProjectDefinition(projectMeta: ProjectMeta): Unit = {
    println(ProjectMeta.toJson(projectMeta))
  }
}
