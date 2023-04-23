// Settings
bloopAggregateSourceDependencies in Global := true
val stbVersion = "1.0.0"
lazy val commonSettings = Seq(
  scalaVersion := "3.0.2",
  crossScalaVersions ++= Seq("2.13.5", "3.0.2"),
  libraryDependencies += ("org.scala-lang.modules" %% "scala-swing" % "3.0.0")
    .cross(CrossVersion.for3Use2_13),
  libraryDependencies += ("com.typesafe.akka" %% "akka-actor-typed" % "2.7.0")
    .cross(CrossVersion.for3Use2_13),
  libraryDependencies += ("com.typesafe.akka" %% "akka-stream" % "2.7.0")
    .cross(CrossVersion.for3Use2_13),
  libraryDependencies += ("com.typesafe.akka" %% "akka-http" % "10.5.1")
    .cross(CrossVersion.for3Use2_13),
  libraryDependencies += "com.typesafe" % "config" % "1.4.1",
  libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test",
  libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.0.0",
  libraryDependencies += ("com.typesafe.play" %% "play-json" % "2.9.2")
    .cross(CrossVersion.for3Use2_13),
  libraryDependencies += "com.google.inject" % "guice" % "4.2.3",
  libraryDependencies += ("net.codingwell" %% "scala-guice" % "4.2.11")
    .cross(CrossVersion.for3Use2_13),
  jacocoExcludes := Seq(
    "*de.htwg.se.stb.aview.*",
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
// Custom Tasks
val copyConf = taskKey[Unit]("Copies the application.conf file to the game module")
copyConf := {
  val confFile = (baseDirectory in ThisBuild).value / "src/main/resources/application.conf"
  val targetDirs = Seq(
    (baseDirectory in ThisBuild).value / "dice/src/main/resources/application.conf",
    (baseDirectory in ThisBuild).value / "board/src/main/resources/application.conf",
    (baseDirectory in ThisBuild).value / "player/src/main/resources/application.conf",
    (baseDirectory in ThisBuild).value / "game/src/main/resources/application.conf"
  )
  targetDirs.foreach(targetDir => IO.copyFile(confFile, targetDir))
}
compile in Compile := (compile in Compile).dependsOn(copyConf).value

// Projects
lazy val root = project
  .in(file("."))
  .dependsOn(game)
  .aggregate(game)
  .settings(
    name := "shutthebox",
    version := stbVersion,
    commonSettings)
  .enablePlugins(JacocoCoverallsPlugin)

lazy val game = project
  .in(file("game"))
  .dependsOn(board)
  .aggregate(board)
  .dependsOn(dice)
  .aggregate(dice)
  .dependsOn(player)
  .aggregate(player)
  .settings(
    name := "game",
    version := stbVersion,
    commonSettings,
  )

lazy val board = project
  .in(file("board"))
  .settings(
    name := "board",
    version := stbVersion,
    commonSettings
  )

lazy val dice = project
  .in(file("dice"))
  .settings(
    name := "dice",
    version := stbVersion,
    commonSettings
  )

lazy val player = project
  .in(file("player"))
  .settings(
    name := "player",
    version := stbVersion,
    commonSettings
  )