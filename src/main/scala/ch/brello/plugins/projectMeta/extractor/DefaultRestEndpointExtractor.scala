package ch.brello.plugins.projectMeta.extractor

import ch.brello.plugins.projectMeta.models.dependencies.Dependency
import ch.brello.plugins.projectMeta.models.dependencies.rest.{RestDependency, RestEndpoint}

object DefaultRestEndpointExtractor extends DependencyExtractor {
  override type T = RestDependency

  override def extract(input: String,
      currentDependencies: Map[String, Dependency]): T = {
    val currentDependency = currentDependencies
      .getOrElse(DefaultRestEndpointExtractor.getClass.getSimpleName, RestDependency(List.empty)).asInstanceOf[RestDependency]

    if(input.contains(PropertyConstants.REST_ENDPOINTS_KEY)) {
      RestDependency(
        currentDependency.endpoints ++ input.split("=")(1).split(",").map(RestEndpoint).toList
      )
    } else {
      throw new Exception
    }
  }
}
