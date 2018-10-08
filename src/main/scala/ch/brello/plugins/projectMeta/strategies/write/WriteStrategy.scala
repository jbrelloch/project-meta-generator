package ch.brello.plugins.projectMeta.strategies.write

import ch.brello.plugins.projectMeta.models.ProjectMeta

trait WriteStrategy {
  def generateProjectDefinition(projectMeta: ProjectMeta): Unit
}
