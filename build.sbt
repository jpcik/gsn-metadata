packageArchetype.java_application

name := "gsn-metadata"

organization := "ch.epfl.lsir"

version := "0.0.1"

scalaVersion := "2.10.1"

crossPaths := false

libraryDependencies ++= Seq(
  "es.upm.fi.oeg.morph" % "morph-querygen" % "1.0.7" exclude("org.slf4j","slf4j-log4j12"),
  "org.apache.jena" % "jena-jdbc-core" % "1.0.1" intransitive,
  "com.openlinksw.virtuoso" % "virt_jena2" % "2.10",
  "com.openlinksw.virtuoso" % "virtjdbc" % "4",
  "org.apache.directory.api" % "api-all" % "1.0.0-M20" intransitive,
  "com.typesafe.play" %% "play" % "2.2.1" intransitive,
  "com.typesafe.akka" %% "akka-actor" % "2.1.2" intransitive,
  "ch.qos.logback" % "logback-classic" % "1.0.13" ,
  "org.scalatest" % "scalatest_2.10" % "2.0.RC1" % "test"
)

resolvers ++= Seq(
  DefaultMavenRepository,
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Gsn external repo" at  "http://gsn.epfl.ch:8081/artifactory/ext-release-local/",
  "Morph repo" at "http://gsn.epfl.ch:8081/artifactory/morphstreams"
)

//mainClass := Some("gsn.Main")

unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(Seq(_))

unmanagedSourceDirectories in Test <<= (scalaSource in Test)(Seq(_))

scalacOptions += "-deprecation"

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

parallelExecution in Test := false

publishTo := Some("Artifactory Realm" at "http://aldebaran.dia.fi.upm.es/artifactory/sstreams-releases-local")

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishMavenStyle := true

publishArtifact in (Compile) := false
