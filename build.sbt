
lazy val flinkVersion           = "1.0.1"
lazy val akkaVersion            = "2.4.3"
lazy val configVersion          = "1.3.0"
lazy val jeromqVersion          = "0.3.5"
lazy val chillVersion           = "0.8.0"
lazy val kryoSerializersVersion = "0.37"
lazy val nscalaTimeVersion      = "2.8.0"
lazy val scalaLoggingVersion    = "3.1.0"
lazy val logbackVersion         = "1.1.2"
lazy val scalaTestVersion       = "3.0.0-M15"
lazy val scalaReflect           = "2.11.7"
lazy val ipdeVersion            = "7.5.5-develop"
lazy val sprayVersion           = "1.3.2"
lazy val avroVersion            = "1.8.0"
lazy val json4sVersion          = "3.3.0"




enablePlugins(GitVersioning, GitBranchPrompt)

lazy val commonSettings = Seq(
  organization         := "nl.tkp.poc",
  git.useGitDescribe   := true,
  scalaVersion         := "2.11.7",
  scalacOptions        := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8","-Xmax-classfile-name","78"),
//  maintainer in Docker := "Your Name <your@email.address>",
  resolvers += "tkp-artifactory" at "http://artifactory.intra.tkppensioen.nl/artifactory/maven-global-repo",
  resolvers += Resolver.mavenLocal
)


lazy val nota_opslag_api = project.in(file("nota-opslag-api"))
  .settings(commonSettings: _*)
  .settings(
    name := "nota-opslag-api",
    libraryDependencies ++= Seq(
       "org.apache.flink"            %% "flink-scala"          % flinkVersion           % "provided"
     , "org.apache.flink"            %% "flink-clients"        % flinkVersion           % "provided"
     , "com.typesafe.scala-logging"  %% "scala-logging"        % scalaLoggingVersion
     , "com.github.nscala-time"      %% "nscala-time"          % nscalaTimeVersion
     , "com.twitter"                 %% "chill"                % chillVersion            exclude("com.esotericsoftware","kryo-shaded")
     , "com.twitter"                 %% "chill-bijection"      % chillVersion            exclude("com.esotericsoftware","kryo-shaded")
     , "de.javakaffee"               %  "kryo-serializers"     % kryoSerializersVersion  exclude("com.esotericsoftware","kryo-shaded")
                                                                                         exclude("com.esotericsoftware","kryo")
                                                                                         exclude("org.ow2.asm","asm")
     , "org.json4s"                  %% "json4s-ext"           % json4sVersion
     , "org.json4s"                  %  "json4s-jackson_2.11"  % "3.3.0"
    )
  )

lazy val nota_inlezen = project.in(file("nota-inlezen"))
  .settings(commonSettings: _*)
  .enablePlugins(JavaServerAppPackaging, DockerPlugin)
  .settings(
    name := "nota-inlezen",
    libraryDependencies ++= Seq(
       "org.scalactic"              %% "scalactic"        % scalaTestVersion
      ,"org.scalatest"              %% "scalatest"        % scalaTestVersion       % "test"
)
  )
  .dependsOn(nota_opslag_api)


fork in run := true
