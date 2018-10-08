package ch.brello.plugins.projectMeta.strategies

import ch.brello.plugins.projectMeta.models.ProjectMeta

trait GenerationStrategy {
  def generateProjectDefinition(projectMeta: ProjectMeta): Unit
}
