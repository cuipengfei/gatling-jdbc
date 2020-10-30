organization := "com.github.cuipengfei"
name := "jdbc-gatling"
scalaVersion := "2.12.12"
libraryDependencies ++= Seq(
  "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.4.1",
  "io.gatling" % "gatling-test-framework" % "3.4.1",
  "org.scalikejdbc" %% "scalikejdbc" % "3.5.0",
  "com.h2database" % "h2" % "1.4.200",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "mysql" % "mysql-connector-java" % "8.0.22" % "test",
  "org.postgresql" % "postgresql" % "42.2.18" % "test",
  "org.scalatest" %% "scalatest" % "3.0.9" % "test",
  "org.testcontainers" % "postgresql" % "1.15.0-rc2" % "test",
  "org.testcontainers" % "mysql" % "1.15.0-rc2" % "test"
)
enablePlugins(GatlingPlugin)

parallelExecution in Test := false

//everything below this line is related to the project release
homepage := Some(url("https://github.com/cuipengfei/gatling-jdbc"))
scmInfo := Some(ScmInfo(url("https://github.com/cuipengfei/gatling-jdbc"), "git@github.com:cuipengfei/gatling-jdbc.git"))
developers := List(Developer("cuipengfei",
  "cuipengfei",
  "cuipengfei2008@gmail.com",
  url("https://github.com/cuipengfei")))
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

// Add sonatype repository settings
publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)

sonatypeProfileName := "com.github.cuipengfei"
pomIncludeRepository := { _ => false }
publishArtifact in Test := false
publishMavenStyle := true
publishConfiguration := publishConfiguration.value.withOverwrite(true)
publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true)
releasePublishArtifactsAction := PgpKeys.publishSigned.value
releaseProcess += releaseStepCommand("sonatypeReleaseAll")
