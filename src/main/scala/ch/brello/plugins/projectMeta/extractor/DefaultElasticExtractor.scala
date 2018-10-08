package ch.brello.plugins.projectMeta.extractor

import ch.brello.plugins.projectMeta.models.dependencies.Dependency
import ch.brello.plugins.projectMeta.models.dependencies.database.{DatabaseDependency, DatabaseTable}

case object DefaultElasticExtractor extends DependencyExtractor {
  override type T = DatabaseDependency

  override def extract(input: String,
      currentDependencies: Map[String, Dependency]): T = {
    val currentDependency = currentDependencies
      .getOrElse(DefaultElasticExtractor.getClass.getSimpleName, DatabaseDependency("elastic", "", List.empty)).asInstanceOf[DatabaseDependency]

    if(input.contains(PropertyConstants.ELASTIC_CLUSTER_NAME_KEY)) {
      DatabaseDependency(
        currentDependency.technology,
        input.split("=")(1),
        currentDependency.tables
      )
    } else if(input.contains(PropertyConstants.ELASTIC_INDICES_KEY)) {
      DatabaseDependency(
        currentDependency.technology,
        currentDependency.name,
        input.split("=")(1).split(",").map(DatabaseTable(_, List("read", "write"))).toList
      )
    } else {
      throw new Exception
    }
  }
}
