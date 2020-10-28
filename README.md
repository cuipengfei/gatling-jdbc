# Gatling JDBC Extension
JDBC support for Gatling

[![Build Status](https://travis-ci.org/cuipengfei/gatling-jdbc.svg?branch=master)](https://travis-ci.org/github/cuipengfei/gatling-jdbc)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.cuipengfei/jdbc-gatling_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.cuipengfei/jdbc-gatling_2.12)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.cuipengfei/jdbc-gatling_2.12.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.cuipengfei%22%20AND%20a:%22jdbc-gatling_2.12%22)

The JDBC extension for Gatling was originally created to accompany a blog post that shows how to extend Gatling.
Currently, five SQL operations are supported. See below for the usage.

## :exclamation: Attention :exclamation:

In order to avoid conflicts with `io.gatling:gatling-jdbc` the artifact name has been changed with version 2.0.1.
Instead of `gatling-jdbc` it is now called `jdbc-gatling` (see issue #8). Apart from this, nothing changes. All package names etc. stayed the same.

Also, by forking it from it's original position the group id and the packages have changed!
The correct import is now `com.github.cuipengfei....` and the group id changed to `com.github.cuipengfei`.

## Usage

```scala
libraryDependencies += "com.github.cuipengfei" %% "gatling-jdbc" % "version"
```

This fork added support for update operations.

For usage examples, please refer to the [simulation](https://github.com/cuipengfei/gatling-jdbc/tree/master/src/test/scala/com/github/cuipengfei/gatling/jdbc/simulation) examples in this repo.


### Final

Covering all SQL operations is a lot of work and some special commands might not be required for performance tests.
Please keep in mind that the state of this Gatling extension can be considered experimental. Feel free to leave comments and create pull requests.

## Publishing

Firstly, you gotta have in your home `.sbt/1.0/sonatype.sbt` configured to contain your username and password for Sonatype.
Secondly, open the sbt shell an perform the following steps:
1. `set pgpSecretRing := file("/home/<user>/.sbt/gpg/secring.asc")` or where ever it is
2. `release`

## Executing the intergration tests

If you have to run Docker on your machine as sudo, then to execute the integration tests, sbt has to be started as sudo, too.
Only `sudo sbt gatling:test` will then be allowed to start the container of the databases.

## Acknowledgements

I'd like to thank my former employer [codecentric](https://github.com/codecentric) for providing me the time and space to
get started on this project and transform it to a publishable library.