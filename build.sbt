val scala3Version = "3.0.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "shutthebox",
    version := "1.0.1",

    scalaVersion := scala3Version,
    crossScalaVersions ++= Seq("2.13.5", "3.0.2"),
    libraryDependencies += ("org.scala-lang.modules" %% "scala-swing" % "3.0.0")
      .cross(CrossVersion.for3Use2_13),
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test",
    libraryDependencies += "com.google.inject" % "guice" % "4.2.3",
    libraryDependencies += ("net.codingwell" %% "scala-guice" % "4.2.11")
      .cross(CrossVersion.for3Use2_13),
        
    libraryDependencies ++= {
    // Determine OS version of JavaFX binaries
    lazy val osName = System.getProperty("os.name") match {
      case n if n.startsWith("Linux") => "linux"
      case n if n.startsWith("Mac") => "mac"
      case n if n.startsWith("Windows") => "win"
      case _ => throw new Exception("Unknown platform!")
    }
      Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % "16" classifier osName)
    },

    jacocoExcludes := Seq(
      "*aview.*",
      "*ShutTheBoxModule*",
      ".*Main.*"
    ),
    jacocoReportSettings := JacocoReportSettings(
      "Jacoco Coverage Report",
      None,
      JacocoThresholds(),
      Seq(JacocoReportFormats.ScalaHTML, JacocoReportFormats.XML), // note XML formatter
      "utf-8"),

    jacocoCoverallsServiceName := "github-actions",
    jacocoCoverallsBranch := sys.env.get("CI_BRANCH"),
    jacocoCoverallsPullRequest := sys.env.get("GITHUB_EVENT_NAME"),
    jacocoCoverallsRepoToken := sys.env.get("COVERALLS_REPO_TOKEN")
  )
  .enablePlugins(JacocoCoverallsPlugin)