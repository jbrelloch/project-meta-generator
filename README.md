# project-meta-generator

An SBT plugin to generate a project definition object.

# Installation

This project hasn't been published yet so to use you'll have to pull the project, compile, 
and publishLocal.  Then add the following to your plugins.sbt file:

```
addSbtPlugin("ch.brello" % "project-meta-generator" % "0.1")
```

In your `build.sbt` file enable the plugin with:

```
// -- base project configuration
lazy val yourProject = (project in file("."))
  .settings(...)
  .enablePlugins(ch.brello.plugins.projectMeta.ProjectMetaGenerator)
```

There are two required settings that also need to be defined in the `build.sbt` file:

```
projectDefinitions := List(
  ch.brello.plugins.projectMeta.models.Project(
    "your-project",
    "Your Project Pretty Name",
    "your-project-marathon-name",
    List("technology")
  )
)
projectOwner := ch.brello.plugins.projectMeta.models.Owner("Team Owner", "Pillar Owner")
```

# Usage

To run execute the sbt command `sbt buildProjectMeta` and it will print this to the console:

```
{
  "micro": {
    "name": "your-project",
    "prettyName": "Your Project Pretty Name",
    "marathonName": "your-project-marathon-name",
    "technologies": [
      "technology"
    ]
  },
  "owner": {
    "team": "Team Owner",
    "pillar": "Pillar Owner"
  },
  "dependencies": []
}
```

The default behavior for dependencies is to read the `src/main/resources/local.properties`
file and look for properties that start with these keys:

```
  val KAFKA_CONSUMER_KEY: String = "kafka.topics.input"
  val KAFKA_PRODUCER_KEY: String = "kafka.topics.output"

  val MYSQL_DB_NAME_KEY: String = "database.mysql.url"
  val MYSQL_TABLES_KEY: String = "database.mysql.tables"

  val ELASTIC_CLUSTER_NAME_KEY: String = "database.elastic.clusterName"
  val ELASTIC_INDEX_KEY: String = "database.elastic.index"
```

So for this example properties file:

```
kafka.topics.input.topic1=inputTopic1
kafka.topics.input.topic2=inputTopic2
kafka.topics.output.topic1=outputTopic1

database.mysql.url=jdbc:mysql://127.0.0.1:3306/dbName
database.mysql.tables=table1,table2,table3

database.elastic.clusterName=elasticClusterName
database.elastic.indices=index1,index2
```

The generated project definition would be:

```
{
  "micro": {
    "name": "your-project",
    "prettyName": "Your Project Pretty Name",
    "marathonName": "your-project-marathon-name",
    "technologies": [
      "technology"
    ]
  },
  "owner": {
    "team": "Team Owner",
    "pillar": "Pillar Owner"
  },
  "dependencies": [
      {
        "type": "database",
        "technology": "percona",
        "name": "dbName",
        "tables": [
          {
            "name": "table1",
            "operation": [
              "read",
              "write"
            ]
          },
          {
            "name": "table2",
            "operation": [
              "read",
              "write"
            ]
          },
          {
            "name": "table3",
            "operation": [
              "read",
              "write"
            ]
          }
        ]
      },
      {
        "type": "database",
        "technology": "elastic",
        "name": "elasticClusterName",
        "tables": [
          {
            "name": "index1",
            "operation": [
              "read",
              "write"
            ]
          },
          {
            "name": "index2",
            "operation": [
              "read",
              "write"
            ]
          }
        ]
      },
      {
        "type": "kafka",
        "consumers": [
          {
            "topic": "inputTopic1"
          },
          {
            "topic": "inputTopic2"
          }
        ],
        "producers": [
          {
            "topic": "outputTopic1"
          }
        ]
      }
    ]
}
```

# Advanced

Coming soon...