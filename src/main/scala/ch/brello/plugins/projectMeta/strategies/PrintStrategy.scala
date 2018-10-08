package ch.brello.plugins.projectMeta.strategies

import ch.brello.plugins.projectMeta.models.ProjectMeta

case class PrintStrategy() extends GenerationStrategy {
  override def generateProjectDefinition(projectMeta: ProjectMeta): Unit = {
    println(ProjectMeta.toJson(projectMeta))
  }
}
