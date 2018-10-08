package ch.brello.plugins.projectMeta.extractor

import ch.brello.plugins.projectMeta.models.dependencies.Dependency
import ch.brello.plugins.projectMeta.models.dependencies.database.{DatabaseDependency, DatabaseTable}

case object DefaultPerconaExtractor extends DependencyExtractor {
  override type T = DatabaseDependency

  override def extract(input: String,
      currentDependencies: Map[String, Dependency]): T = {
    val currentDependency = currentDependencies
      .getOrElse(DefaultPerconaExtractor.getClass.getSimpleName, DatabaseDependency("percona", "", List.empty)).asInstanceOf[DatabaseDependency]

    if(input.contains(PropertyConstants.PERCONA_DB_NAME_KEY)) {
      DatabaseDependency(
        currentDependency.technology,
        input.split("=")(1).split("/").last,
        currentDependency.tables
      )
    } else if(input.contains(PropertyConstants.PERCONA_TABLES_KEY)) {
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
