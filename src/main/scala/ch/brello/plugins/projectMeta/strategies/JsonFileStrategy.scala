package ch.brello.plugins.projectMeta.strategies

import ch.brello.plugins.projectMeta.models.ProjectMeta

case class JsonFileStrategy(filename: String) extends GenerationStrategy {
  override def generateProjectDefinition(projectMeta: ProjectMeta): Unit = {

  }
}
