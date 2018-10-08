package ch.brello.plugins.projectMeta.strategies.read

trait ReadStrategy {
  def retrieveProperties: List[String]
}
