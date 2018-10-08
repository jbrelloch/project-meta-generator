package ch.brello.plugins.projectMeta.extractor

import ch.brello.plugins.projectMeta.models.dependencies.database.DatabaseDependency
import org.scalatest.WordSpec

class DefaultPerconaExtractorSpec extends WordSpec {
  "retrieve percona db name topic" in {
    val dbName = "perconaDbName"
    val input = s"${PropertyConstants.PERCONA_DB_NAME_KEY}=$dbName"

    val generatedDependency = DefaultPerconaExtractor.extract(input, Map.empty)

    assert(generatedDependency != null)
    assert(generatedDependency.technology == "percona")
    assert(generatedDependency.name == dbName)
    assert(generatedDependency.tables.isEmpty)
  }

  "retrieve percona table names topic" in {
    val table1 = "table1"
    val table2 = "table2"
    val input = s"${PropertyConstants.PERCONA_TABLES_KEY}=$table1,$table2"

    val generatedDependency = DefaultPerconaExtractor.extract(input, Map.empty)

    assert(generatedDependency != null)
    assert(generatedDependency.technology == "percona")
    assert(generatedDependency.name == "")
    assert(generatedDependency.tables.size == 2)
    assert(generatedDependency.tables.exists(_.name == table1))
    assert(generatedDependency.tables.exists(_.name == table2))
  }

  "use previous dependency if exists" in {
    val clusterName = "perconaDbName"
    val table1 = "table1"
    val table2 = "table2"
    val input = s"${PropertyConstants.PERCONA_TABLES_KEY}=$table1,$table2"

    val existingDependency = DatabaseDependency(
      "percona",
      clusterName,
      List.empty
    )

    val generatedDependency = DefaultPerconaExtractor.extract(input, Map(DefaultPerconaExtractor.getClass.getSimpleName -> existingDependency))

    assert(generatedDependency != null)
    assert(generatedDependency.technology == "percona")
    assert(generatedDependency.name == clusterName)
    assert(generatedDependency.tables.size == 2)
    assert(generatedDependency.tables.exists(_.name == table1))
    assert(generatedDependency.tables.exists(_.name == table2))
  }

  "throw exception on unknown key" in {
    val input = s"${PropertyConstants.KAFKA_PRODUCER_KEY}=throwException"

    val ex = intercept[Exception] {
      DefaultPerconaExtractor.extract(input, Map.empty)
    }

    assert(ex != null)
  }
}
