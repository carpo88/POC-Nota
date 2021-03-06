

logLevel := sbt.Level.Info

//resolvers ++= Seq(
//  Resolver.mavenLocal
//  ,Resolver.typesafeRepo("releases")
//)

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.0-RC1")

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.8.5")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.3")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")

