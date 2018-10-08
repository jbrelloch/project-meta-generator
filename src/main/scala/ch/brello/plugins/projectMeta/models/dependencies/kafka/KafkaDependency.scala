package ch.brello.plugins.projectMeta.models.dependencies.kafka

import ch.brello.plugins.projectMeta.models.dependencies.Dependency
import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}

case class KafkaDependency @JsonCreator() (
    @JsonProperty("type")
    override val dependencyType: String,
    consumers: List[KafkaConsumerTopic],
    producers: List[KafkaProducerTopic]) extends Dependency

object KafkaDependency {
  def apply(): KafkaDependency = new KafkaDependency("kafka", List.empty[KafkaConsumerTopic], List.empty[KafkaProducerTopic])
  def apply(consumers: List[KafkaConsumerTopic],
      producers: List[KafkaProducerTopic]): KafkaDependency = new KafkaDependency("kafka", consumers, producers)
}
