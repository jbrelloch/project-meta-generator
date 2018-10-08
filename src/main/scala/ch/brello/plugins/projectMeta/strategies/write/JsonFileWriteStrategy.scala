package ch.brello.plugins.projectMeta.strategies.write

import java.io.{FileOutputStream, PrintWriter}

import ch.brello.plugins.projectMeta.models.ProjectMeta

case class JsonFileWriteStrategy(filename: String) extends WriteStrategy {
  override def generateProjectDefinition(projectMeta: ProjectMeta): Unit = {
    val writer = new PrintWriter(new FileOutputStream(filename, false))

    writer.write(ProjectMeta.toJson(projectMeta))

    writer.close()
  }
}
