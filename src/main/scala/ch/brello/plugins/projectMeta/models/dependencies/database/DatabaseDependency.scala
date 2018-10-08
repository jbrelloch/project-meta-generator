package ch.brello.plugins.projectMeta.models.dependencies.database

import ch.brello.plugins.projectMeta.models.dependencies.Dependency
import com.fasterxml.jackson.annotation.{JsonCreator, JsonIgnore, JsonProperty}

case class DatabaseDependency @JsonCreator() (
    @JsonProperty("type")
    override val dependencyType: String,
    technology: String,
    name: String,
    tables: List[DatabaseTable]) extends Dependency {

  @JsonIgnore
  def this(technology: String,
      name: String,
      tables: List[DatabaseTable]) = this("database", technology, name, tables)
}

object DatabaseDependency {
  def apply(technology: String,
      name: String,
      tables: List[DatabaseTable]): DatabaseDependency = new DatabaseDependency(technology, name, tables)
}
