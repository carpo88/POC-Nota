
//lazy val flinkVersion           = "1.0.3"
lazy val flinkVersion           = "1.1-SNAPSHOT"
lazy val configVersion          = "1.3.0"
lazy val jeromqVersion          = "0.3.5"
lazy val chillVersion           = "0.7.4" // 074 voor compatibility met flink , anders "0.8.0"
lazy val kryoSerializersVersion = "0.37"
lazy val nscalaTimeVersion      = "2.8.0"
lazy val scalaLoggingVersion    = "2.1.2"  // voor 2.11 --> "3.1.0" voor 2.10 --> "2.1.2"
lazy val logbackVersion         = "1.1.2"
lazy val scalaTestVersion       = "3.0.0-M15"
lazy val scalaReflect           = "2.10.6"
lazy val ipdeVersion            = "7.5.5-develop"
lazy val sprayVersion           = "1.3.2"
lazy val avroVersion            = "1.8.0"
lazy val json4sVersion          = "3.3.0"
lazy val scalikejdbcVersion     = "2.4.1"
lazy val ojdbcVersion           = "7"



enablePlugins(GitVersioning, GitBranchPrompt)

lazy val commonSettings = Seq(
  organization         := "nl.tkp.poc",
  git.useGitDescribe   := true,
  scalaVersion         := "2.10.4",
  scalacOptions        := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8","-Xmax-classfile-name","78"),
  maintainer in Docker := "Your Name <your@email.address>",
  resolvers += "tkp-apache-public" at "http://artifactory.shs.tkp/artifactory/apache-public",
  resolvers += "tkp-global-repo" at "http://artifactory.shs.tkp/artifactory/maven-global-repo",
  resolvers += "conjars-repo" at "http://conjars.org/repo"
//
//  resolvers += Resolver.mavenLocal,
//  resolvers += "tkp-artifactory" at "http://artifactory.intra.tkppensioen.nl/artifactory/maven-global-repo",
//  resolvers += "Apache Snapshot    Repository" at "https://repository.apache.org/content/repositories/snapshots/",
//  resolvers += "Apache Development Repository" at "https://repository.apache.org/content/repositories/releases/",
)


lazy val nota_opslag_api = project.in(file("nota-opslag-api"))
  .settings(commonSettings: _*)
  .settings(
    name := "nota-opslag-api",
    libraryDependencies ++= Seq(
       "org.apache.flink"            %% "flink-scala"          % flinkVersion           % "provided"
     , "org.apache.flink"            %% "flink-clients"        % flinkVersion           % "provided"
//     , "org.slf4j"                   % "slf4j-nop"             % "1.6.4"
//     , "com.github.nscala-time"      %% "nscala-time"          % nscalaTimeVersion
//     , "com.twitter"                 %% "chill"                % chillVersion            exclude("com.esotericsoftware","kryo-shaded")
//     , "com.twitter"                 %% "chill-bijection"      % chillVersion            exclude("com.esotericsoftware","kryo-shaded")
     , "de.javakaffee"               %  "kryo-serializers"     % kryoSerializersVersion  exclude("com.esotericsoftware","kryo-shaded")
                                                                                         exclude("com.esotericsoftware","kryo")
                                                                                         exclude("org.ow2.asm","asm")
//     , "org.json4s"                  %% "json4s-ext"           % json4sVersion
//     , "org.json4s"                  %  "json4s-jackson_2.11"  % "3.3.0"
    )
  )

lazy val nota_inlezen = project.in(file("nota-inlezen"))
  .settings(commonSettings: _*)
  .enablePlugins(JavaServerAppPackaging, DockerPlugin)
  .settings(
    name := "nota-inlezen",
    libraryDependencies ++= Seq(
       "com.typesafe.scala-logging" % "scala-logging-slf4j_2.10" % "2.1.2"
      ,"org.apache.flink"            %% "flink-scala"             % flinkVersion
      , "org.apache.flink"          %% "flink-clients"           % flinkVersion
      , "org.apache.flink"          % "flink-libraries"           % flinkVersion
      , "org.apache.flink"           % "flink-batch-connectors"  % flinkVersion
      , "ojdbc" % "ojdbc" % ojdbcVersion
      , "org.apache.flink"           %% "flink-table"  % flinkVersion    //pomOnly()
      , "org.apache.flink"           % "flink-jdbc"  % flinkVersion    //pomOnly()
      , "org.apache.derby" % "derby" % "10.12.1.1"
    )
  )
  .dependsOn(nota_opslag_api)


fork in run := true
