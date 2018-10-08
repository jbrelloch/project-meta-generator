package ch.brello.plugins.projectMeta.extractor

import ch.brello.plugins.projectMeta.models.dependencies.rest.{RestDependency, RestEndpoint}
import org.scalatest.WordSpec

class DefaultRestEndpointExtractorSpec extends WordSpec {
  "retrieve rest endpoints" in {
    val endpoint1 = "testEndpoint1"
    val endpoint2 = "testEndpoint2"
    val endpoints = s"$endpoint1,$endpoint2"
    val input = s"${PropertyConstants.REST_ENDPOINTS_KEY}.endpoints=$endpoints"

    val generatedDependency = DefaultRestEndpointExtractor.extract(input, Map.empty)

    assert(generatedDependency != null)
    assert(generatedDependency.endpoints.size == 2)
    assert(generatedDependency.endpoints.exists(_.micro == endpoint1))
    assert(generatedDependency.endpoints.exists(_.micro == endpoint2))
  }

  "use previous dependency if exists" in {
    val endpoint1 = "testEndpoint1"
    val endpoint2 = "testEndpoint2"
    val endpoints = s"$endpoint1,$endpoint2"
    val input = s"${PropertyConstants.REST_ENDPOINTS_KEY}.endpoints=$endpoints"

    val existingEndpoint = "existingEndpoint"

    val generatedDependency = DefaultRestEndpointExtractor.extract(
      input,
      Map(DefaultRestEndpointExtractor.getClass.getSimpleName -> RestDependency(List(RestEndpoint(existingEndpoint))))
    )

    assert(generatedDependency != null)
    assert(generatedDependency.endpoints.size == 3)
    assert(generatedDependency.endpoints.exists(_.micro == endpoint1))
    assert(generatedDependency.endpoints.exists(_.micro == endpoint2))
    assert(generatedDependency.endpoints.exists(_.micro == existingEndpoint))
  }

  "throw exception on unknown key" in {
    val input = s"${PropertyConstants.KAFKA_PRODUCER_KEY}=throwException"

    val ex = intercept[Exception] {
      DefaultRestEndpointExtractor.extract(input, Map.empty)
    }

    assert(ex != null)
  }
}
