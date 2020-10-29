name := """impaqts-configurator"""
organization := "it.drwolf.impaqts"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.3"

libraryDependencies += guice

libraryDependencies ++= Seq(
  filters,
  javaJdbc,
  javaJpa,
  "org.hibernate" % "hibernate-core" % "5.3.6.Final" // replace by your jpa implementation
)

// https://mvnrepository.com/artifact/mysql/mysql-connector-java
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.44"



