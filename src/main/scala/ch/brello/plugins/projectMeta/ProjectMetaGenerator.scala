package ch.brello.plugins.projectMeta

import ch.brello.plugins.projectMeta.extractor.{DefaultExtractors, DependencyExtractor}
import ch.brello.plugins.projectMeta.models.dependencies.Dependency
import ch.brello.plugins.projectMeta.models.{Owner, Project, ProjectMeta}
import ch.brello.plugins.projectMeta.strategies.read.{FileReadStrategy, ReadStrategy}
import ch.brello.plugins.projectMeta.strategies.write.{JsonPrintlnWriteStrategy, WriteStrategy}
import sbt._

object ProjectMetaGenerator extends AutoPlugin  {
  object autoImport {
    val projectDefinitions = settingKey[List[Project]]("The list of project descriptions")
    val projectOwner = settingKey[Owner]("The owner")
    val generatedDefinitionPrefix = settingKey[String]("The prefix to be applied to each model generation")
    val writeStrategy = settingKey[WriteStrategy]("The method to write the model")
    val readStrategy = settingKey[ReadStrategy]("The method to read the properties")
    val extractorMap = settingKey[Map[String, DependencyExtractor]]("The map used to extract dependencies from the properties file")

    val buildProjectMeta = inputKey[Unit]("Writes project meta definition")
  }

  import autoImport._

  override lazy val buildSettings: Seq[Def.Setting[_]] = Seq(
    extractorMap := DefaultExtractors.map,
    generatedDefinitionPrefix := "micro_meta",
    writeStrategy := JsonPrintlnWriteStrategy(),
    readStrategy := FileReadStrategy("src/main/resources/local.properties")
  )

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    projectMeta
  )

  def projectMeta: Setting[_] = buildProjectMeta := {
    projectDefinitions.value.foreach(projectDef => {

      val projectMeta = ProjectMeta(
        projectDef,
        projectOwner.value,
        processProperties(readStrategy.value.retrieveProperties, projectDef.propertyIdentifier, extractorMap.value)
      )

      writeStrategy.value.generateProjectDefinition(projectMeta)
    })
  }

  def processProperties(propertyFileLines: List[String],
      projectIdentifier: Option[String],
      extractorMap: Map[String, DependencyExtractor]): List[Dependency] = {
    val dependencies = scala.collection.mutable.Map[String, Dependency]()

    val prependProjectIdentifier = projectIdentifier.map(_ + ".").getOrElse("")

    propertyFileLines.foreach(inputLine => {
      extractorMap.find {
        case (key , _) => inputLine.startsWith(prependProjectIdentifier + key)
      }.foreach {
        case (_, extractor) =>
          val processedDependency = extractor.extract(inputLine, dependencies.toMap)
          dependencies.put(extractor.getClass.getSimpleName, processedDependency)
      }
    })

    dependencies.values.toList
  }
}
