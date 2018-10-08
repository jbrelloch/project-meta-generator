package ch.brello.plugins.projectMeta.strategies.read

import scala.io.Source

case class FileReadStrategy(filePath: String) extends ReadStrategy {
  def retrieveProperties: List[String] = Source.fromFile(filePath).getLines().toList
}
