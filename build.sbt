name := "explore-free"

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck"                % "1.10.1" % "test",
  "org.specs2" %% "specs2-core" % "2.4.15" % "test",
  "org.typelevel" %% "shapeless-scalacheck" % "0.3",
  "org.typelevel" %% "shapeless-spire" % "0.3",
  "org.typelevel" %% "shapeless-scalaz" % "0.3",
  "org.scalaz"     %% "scalaz-core"               % "7.1.0")
