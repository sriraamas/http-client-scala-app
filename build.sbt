import Dependencies._

ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "http-client-scala-app",
    libraryDependencies += scalaTest % Test,
    assembly / assemblyJarName := "http-client-deploy.jar",
  )
libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.13"
libraryDependencies += "org.apache.httpcomponents" % "httpcore" % "4.4.13"
