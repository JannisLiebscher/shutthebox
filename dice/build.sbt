fork := true
scalaVersion := "3.0.2"
crossScalaVersions ++= Seq("2.13.5", "3.1.1")
libraryDependencies += ("org.scala-lang.modules" %% "scala-swing" % "3.0.0")
    .cross(CrossVersion.for3Use2_13)
  libraryDependencies += ("com.typesafe.akka" %% "akka-actor-typed" % "2.7.0")
    .cross(CrossVersion.for3Use2_13)
  libraryDependencies += ("com.typesafe.akka" %% "akka-stream" % "2.7.0")
    .cross(CrossVersion.for3Use2_13)
  libraryDependencies += ("com.typesafe.akka" %% "akka-http" % "10.5.1")
    .cross(CrossVersion.for3Use2_13)
  libraryDependencies += "com.typesafe" % "config" % "1.4.1"
  libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10"
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"
  libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.0.0"
  libraryDependencies += ("com.typesafe.play" %% "play-json" % "2.9.2")
    .cross(CrossVersion.for3Use2_13)
  libraryDependencies += "com.google.inject" % "guice" % "4.2.3"
  libraryDependencies += ("net.codingwell" %% "scala-guice" % "4.2.11")
    .cross(CrossVersion.for3Use2_13)