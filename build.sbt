name := "explore-free"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck"                % "1.10.1" % "test",
  "org.specs2"     %% "specs2"                    % "2.3.8"  % "test",
  "com.chuusai"    %% "shapeless"                 % "2.0.0" cross CrossVersion.full,
  "org.typelevel"  %% "shapeless-scalacheck"      % "0.3-SNAPSHOT",
  "org.scalaz"     %% "scalaz-core"               % "7.1.0-RC1")
