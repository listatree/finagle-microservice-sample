name := """finagle-microservice"""

version := "1.0"

scalaVersion := "2.11.1"

val finagleVersion = "6.24.0"

libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-http" % finagleVersion,
  "com.twitter" %% "finagle-stats" % finagleVersion
)
