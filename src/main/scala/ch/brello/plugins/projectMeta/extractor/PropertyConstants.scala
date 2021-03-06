package ch.brello.plugins.projectMeta.extractor

object PropertyConstants {
  val REST_ENDPOINTS_KEY: String = "rest.endpoints"

  val KAFKA_CONSUMER_KEY: String = "kafka.topics.input"
  val KAFKA_PRODUCER_KEY: String = "kafka.topics.output"

  val PERCONA_DB_NAME_KEY: String = "database.mysql.url"
  val PERCONA_TABLES_KEY: String = "database.mysql.tables"

  val ELASTIC_CLUSTER_NAME_KEY: String = "database.elastic.clusterName"
  val ELASTIC_INDICES_KEY: String = "database.elastic.indices"
}
