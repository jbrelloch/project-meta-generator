package ch.brello.plugins.projectMeta.extractor

import ch.brello.plugins.projectMeta.models.dependencies.kafka.{KafkaDependency, KafkaProducerTopic}
import org.scalatest.WordSpec

class DefaultKafkaExtractorSpec extends WordSpec {
  "retrieve kafka consumer topic" in {
    val topicName = "KafkaTopicName"
    val input = s"${PropertyConstants.KAFKA_CONSUMER_KEY}=$topicName"

    val generatedDependency = DefaultKafkaExtractor.extract(input, Map.empty)

    assert(generatedDependency != null)
    assert(generatedDependency.consumers.size == 1)
    assert(generatedDependency.consumers.head.topic == topicName)
  }

  "retrieve kafka producer topic" in {
    val topicName = "KafkaTopicName"
    val input = s"${PropertyConstants.KAFKA_PRODUCER_KEY}=$topicName"

    val generatedDependency = DefaultKafkaExtractor.extract(input, Map.empty)

    assert(generatedDependency != null)
    assert(generatedDependency.producers.size == 1)
    assert(generatedDependency.producers.head.topic == topicName)
  }

  "use previous dependency if exists" in {
    val consumerTopicName1 = "KafkaConsumerTopicName1"
    val consumerTopicName2 = "KafkaConsumerTopicName2"
    val producerTopicName = "KafkaProducerTopicName"
    val input1 = s"${PropertyConstants.KAFKA_CONSUMER_KEY}.topic1=$consumerTopicName1"
    val input2 = s"${PropertyConstants.KAFKA_CONSUMER_KEY}.topic2=$consumerTopicName2"

    val existingDependency = KafkaDependency(
      List.empty,
      List(KafkaProducerTopic(producerTopicName))
    )

    val intermediateDependency = DefaultKafkaExtractor.extract(input1, Map(DefaultKafkaExtractor.getClass.getSimpleName -> existingDependency))
    val generatedDependency = DefaultKafkaExtractor.extract(input2, Map(DefaultKafkaExtractor.getClass.getSimpleName -> intermediateDependency))

    assert(generatedDependency != null)
    assert(generatedDependency.consumers.size == 2)
    assert(generatedDependency.consumers.exists(_.topic == consumerTopicName1))
    assert(generatedDependency.consumers.exists(_.topic == consumerTopicName2))
    assert(generatedDependency.producers.size == 1)
    assert(generatedDependency.producers.head.topic == producerTopicName)
  }

  "throw exception on unknown key" in {
    val input = s"${PropertyConstants.ELASTIC_INDICES_KEY}=throwException"

    val ex = intercept[Exception] {
      DefaultKafkaExtractor.extract(input, Map.empty)
    }

    assert(ex != null)
  }
}
