package ch.brello.plugins.projectMeta.extractor

import ch.brello.plugins.projectMeta.models.dependencies.database.DatabaseDependency
import org.scalatest.WordSpec

class DefaultElasticExtractorSpec extends WordSpec {
  "retrieve elastic cluster name" in {
    val clusterName = "elasticClusterName"
    val input = s"${PropertyConstants.ELASTIC_CLUSTER_NAME_KEY}=$clusterName"

    val generatedDependency = DefaultElasticExtractor.extract(input, Map.empty)

    assert(generatedDependency != null)
    assert(generatedDependency.technology == "elastic")
    assert(generatedDependency.name == clusterName)
    assert(generatedDependency.tables.isEmpty)
  }

  "retrieve elastic indices" in {
    val index1 = "index1"
    val index2 = "index2"
    val input = s"${PropertyConstants.ELASTIC_INDICES_KEY}=$index1,$index2"

    val generatedDependency = DefaultElasticExtractor.extract(input, Map.empty)

    assert(generatedDependency != null)
    assert(generatedDependency.technology == "elastic")
    assert(generatedDependency.name == "")
    assert(generatedDependency.tables.size == 2)
    assert(generatedDependency.tables.exists(_.name == index1))
    assert(generatedDependency.tables.exists(_.name == index2))
  }

  "use previous dependency if exists" in {
    val clusterName = "elasticClusterName"
    val index1 = "index1"
    val index2 = "index2"
    val input = s"${PropertyConstants.ELASTIC_INDICES_KEY}=$index1,$index2"

    val existingDependency = DatabaseDependency(
      "elastic",
      clusterName,
      List.empty
    )

    val generatedDependency = DefaultElasticExtractor.extract(input, Map(DefaultElasticExtractor.getClass.getSimpleName -> existingDependency))

    assert(generatedDependency != null)
    assert(generatedDependency.technology == "elastic")
    assert(generatedDependency.name == clusterName)
    assert(generatedDependency.tables.size == 2)
    assert(generatedDependency.tables.exists(_.name == index1))
    assert(generatedDependency.tables.exists(_.name == index2))
  }

  "throw exception on unknown key" in {
    val input = s"${PropertyConstants.KAFKA_PRODUCER_KEY}=throwException"

    val ex = intercept[Exception] {
      DefaultElasticExtractor.extract(input, Map.empty)
    }

    assert(ex != null)
  }
}
