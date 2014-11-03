import sbt._
import sbt.Keys._
import util._
import play._
import play.PlayImport.PlayKeys._
import scala.scalajs.sbtplugin.ScalaJSPlugin
import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._
import Dependencies._


object ApplicationBuild extends Build {

  val common = Seq(
    scalaVersion := "2.11.4",
    scalacOptions ++= Seq("-feature", "-deprecation")
  )

  lazy val root = Project("root", file(".")).aggregate(
    uav,
    backend,
    frontend
  )

  lazy val uav = (
    Project("vfd-uav", file("vfd-uav"))
    settings(common: _*)
    settings(
      libraryDependencies ++= Seq(
        akkaActor,
        flow,
        flowNative
      )
    )
  )

  lazy val backend = (
    Project("vfd-backend", file("vfd-backend"))
    enablePlugins(PlayScala)
    settings(common: _*)
    settings(
      relativeSourceMaps := true,
      resolvers += Resolver.url("scala-js-releases", url("http://dl.bintray.com/content/scala-js/scala-js-releases"))(Resolver.ivyStylePatterns),
      libraryDependencies ++= Seq(
        bootstrap,
        fontawesome,
        jquery
      )
    )
    dependsOn(uav)
    dependsOnJs(frontend)
  )

  lazy val frontend = (
    Project("vfd-frontend", file("vfd-frontend"))
    settings(ScalaJSPlugin.scalaJSSettings: _*)
    settings(common: _*)
    settings(
      libraryDependencies ++= Seq(
        rx,
        dom,
        tag
      )
    )
  )

}