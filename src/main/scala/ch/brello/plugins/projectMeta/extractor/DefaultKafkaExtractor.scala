package ch.brello.plugins.projectMeta.extractor

import ch.brello.plugins.projectMeta.models.dependencies.Dependency
import ch.brello.plugins.projectMeta.models.dependencies.kafka.{KafkaConsumerTopic, KafkaDependency, KafkaProducerTopic}

case object DefaultKafkaExtractor extends DependencyExtractor {
  override type T = KafkaDependency

  override def extract(input: String,
      currentDependencies: Map[String, Dependency]): T = {
    val currentDependency = currentDependencies
      .getOrElse(KafkaDependency.getClass.getSimpleName, KafkaDependency(List.empty, List.empty)).asInstanceOf[KafkaDependency]

    if(input.contains(PropertyConstants.KAFKA_CONSUMER_KEY)) {
      KafkaDependency(
        currentDependency.consumers ++ List(KafkaConsumerTopic(input.split("=")(1))),
        currentDependency.producers
      )
    } else if(input.contains(PropertyConstants.KAFKA_CONSUMER_KEY)) {
      KafkaDependency(
        currentDependency.consumers,
        currentDependency.producers ++ List(KafkaProducerTopic(input.split("=")(1)))
      )
    } else {
      throw new Exception
    }
  }
}
