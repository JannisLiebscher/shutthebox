val scala3Version = "3.0.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "main.scala.de.htwg.shutthebox",
    version := "1.0.1",
    scalaVersion := scala3Version,
    crossScalaVersions ++= Seq("2.13.5", "3.0.2"),
    libraryDependencies += ("org.scala-lang.modules" %% "scala-swing" % "3.0.0")
      .cross(CrossVersion.for3Use2_13),
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test",
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.0.0",
    libraryDependencies += ("com.typesafe.play" %% "play-json" % "2.9.2")
      .cross(CrossVersion.for3Use2_13),
    libraryDependencies += "com.google.inject" % "guice" % "4.2.3",
    libraryDependencies += ("net.codingwell" %% "scala-guice" % "4.2.11")
      .cross(CrossVersion.for3Use2_13),
    jacocoExcludes := Seq(
      "*aview.*",
      "*ShutTheBoxModule*",
      "*Main*"
    ),
    jacocoReportSettings := JacocoReportSettings(
      "Jacoco Coverage Report",
      None,
      JacocoThresholds(),
      Seq(
        JacocoReportFormats.ScalaHTML,
        JacocoReportFormats.XML
      ), // note XML formatter
      "utf-8"
    ),
    jacocoCoverallsServiceName := "github-actions",
    jacocoCoverallsBranch := sys.env.get("CI_BRANCH"),
    jacocoCoverallsPullRequest := sys.env.get("GITHUB_EVENT_NAME"),
    jacocoCoverallsRepoToken := sys.env.get("COVERALLS_REPO_TOKEN")
  )
  .enablePlugins(JacocoCoverallsPlugin)
