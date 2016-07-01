
libraryDependencies += "org.hsqldb" % "hsqldb" % "2.3.2"
libraryDependencies += "ojdbc" % "ojdbc" % "7"


logLevel := sbt.Level.Info


addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.0-RC1")

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.8.5")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.3")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")

addSbtPlugin("org.scalikejdbc" %% "scalikejdbc-mapper-generator" % "2.4.1")

