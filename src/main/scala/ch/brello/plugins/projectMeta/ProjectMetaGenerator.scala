package ch.brello.plugins.projectMeta

import ch.brello.plugins.projectMeta.extractor.{DefaultExtractors, DependencyExtractor}
import ch.brello.plugins.projectMeta.models.dependencies.Dependency
import ch.brello.plugins.projectMeta.models.{Owner, Project, ProjectMeta}
import ch.brello.plugins.projectMeta.strategies.{GenerationStrategy, PrintStrategy}
import sbt._

import scala.io.Source

object ProjectMetaGenerator extends AutoPlugin  {
  object autoImport {
    val projectDefinitions = settingKey[List[Project]]("The list of project descriptions")
    val projectOwner = settingKey[Owner]("The owner")
    val dependencyFileName = settingKey[String]("The file name to read dependencies from")
    val generatedDefinitionPrefix = settingKey[String]("The prefix to be applied to each model generation")
    val generationStrategy = settingKey[GenerationStrategy]("The method to generate the model")
    val extractorMap = settingKey[Map[String, DependencyExtractor]]("The map used to extract dependencies from the properties file")

    val buildProjectMeta = inputKey[Unit]("Writes project meta definition")
  }

  import autoImport._

  override lazy val buildSettings: Seq[Def.Setting[_]] = Seq(
    extractorMap := DefaultExtractors.map,
    dependencyFileName := "src/main/resources/local.properties",
    generatedDefinitionPrefix := "micro_meta",
    generationStrategy := PrintStrategy()
  )

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    projectMeta
  )

  def projectMeta: Setting[_] = buildProjectMeta := {
    projectDefinitions.value.foreach(projectDef => {

      val projectMeta = ProjectMeta(
        projectDef,
        projectOwner.value,
        processPropertyFile(retrievePropertyFile(dependencyFileName.value), projectDef.propertyIdentifier, extractorMap.value)
      )

      generationStrategy.value.generateProjectDefinition(projectMeta)
    })
  }

  def retrievePropertyFile(fileName: String): Iterator[String] = Source.fromFile(fileName).getLines()

  def processPropertyFile(propertyFileLines: Iterator[String],
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
