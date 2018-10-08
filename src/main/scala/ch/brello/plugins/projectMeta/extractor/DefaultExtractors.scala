package ch.brello.plugins.projectMeta.extractor

object DefaultExtractors {
  val map = Map(
    (PropertyConstants.KAFKA_CONSUMER_KEY, DefaultKafkaExtractor),
    (PropertyConstants.KAFKA_PRODUCER_KEY, DefaultKafkaExtractor),
    (PropertyConstants.PERCONA_DB_NAME_KEY, DefaultPerconaExtractor),
    (PropertyConstants.PERCONA_TABLES_KEY, DefaultPerconaExtractor),
    (PropertyConstants.ELASTIC_CLUSTER_NAME_KEY, DefaultElasticExtractor),
    (PropertyConstants.ELASTIC_INDICES_KEY, DefaultElasticExtractor),
    (PropertyConstants.REST_ENDPOINTS_KEY, DefaultRestEndpointExtractor)
  )
}
