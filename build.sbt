import Dependencies._

ThisBuild / scalaVersion     := "3.3.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "fr.xela"
ThisBuild / organizationName := "xela85"

val fs2Version = "3.9.3"
val monocleVersion = "3.2.0"
lazy val root = (project in file("."))
  .settings(
    name := "advent-of-code-2022",
    libraryDependencies ++= Seq(
      "co.fs2" %% "fs2-core" % fs2Version,
      "co.fs2" %% "fs2-io" % fs2Version,
      "dev.optics" %% "monocle-core" % monocleVersion,
      "dev.optics" %% "monocle-macro" % monocleVersion)
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
