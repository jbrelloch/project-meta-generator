package ch.brello.plugins.projectMeta

import ch.brello.plugins.projectMeta.extractor.DefaultExtractors
import ch.brello.plugins.projectMeta.models.dependencies.kafka.KafkaDependency
import org.scalatest.WordSpec

class ProjectMetaGeneratorSpec extends WordSpec {
  "kafka parsing test" in {
    val line1 = "kafka.topics.input.test1=inputTopic1"
    val line2 = "kafka.topics.input.test2=inputTopic2"
    val line3 = "kafka.topics.output.test1=outputTopic1"

    val lineIterator = Iterator(line1, line2, line3)

    val processedPropertyFile = ProjectMetaGenerator.processPropertyFile(lineIterator, None, DefaultExtractors.map)

    assert(processedPropertyFile != null)
    assert(processedPropertyFile.size == 1)

    val dependency = processedPropertyFile.head

    assert(dependency.isInstanceOf[KafkaDependency])

    val kafkaDependency = dependency.asInstanceOf[KafkaDependency]

    assert(kafkaDependency.consumers.size == 2)
    assert(kafkaDependency.producers.size == 1)
  }
}