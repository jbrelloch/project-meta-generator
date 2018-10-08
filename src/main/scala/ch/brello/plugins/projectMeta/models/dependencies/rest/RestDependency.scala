package ch.brello.plugins.projectMeta.models.dependencies.rest

import ch.brello.plugins.projectMeta.models.dependencies.Dependency
import com.fasterxml.jackson.annotation.{JsonCreator, JsonIgnore, JsonProperty}

class RestDependency @JsonCreator() (
    @JsonProperty("type")
    override val dependencyType: String,
    val endpoints: List[RestEndpoint]) extends Dependency {

  @JsonIgnore
  def this(endpoints: List[RestEndpoint]) = this("rest", endpoints)
}

object RestDependency {
  def apply(endpoints: List[RestEndpoint]): RestDependency = new RestDependency("rest", endpoints)
}