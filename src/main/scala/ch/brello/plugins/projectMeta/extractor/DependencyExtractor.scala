package ch.brello.plugins.projectMeta.extractor

import ch.brello.plugins.projectMeta.models.dependencies.Dependency

trait DependencyExtractor {
  type T <: Dependency

  def extract(input: String, currentDependencies: Map[String, Dependency]): T
}
