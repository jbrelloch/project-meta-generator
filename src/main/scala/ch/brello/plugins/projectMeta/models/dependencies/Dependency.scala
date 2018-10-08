package ch.brello.plugins.projectMeta.models.dependencies

import com.fasterxml.jackson.annotation.JsonProperty

trait Dependency {
  @JsonProperty("type")
  val dependencyType: String
}
