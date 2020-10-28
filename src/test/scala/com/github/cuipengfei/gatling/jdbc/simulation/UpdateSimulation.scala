package com.github.cuipengfei.gatling.jdbc.simulation

import com.github.cuipengfei.gatling.jdbc.Predef._
import com.github.cuipengfei.gatling.jdbc.builder.column.ColumnHelper._
import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import scalikejdbc.{GlobalSettings, LoggingSQLAndTimeSettings}

class UpdateSimulation extends Simulation {

  val jdbcConfig = jdbc
    .url("jdbc:h2:mem:test;DB_CLOSE_ON_EXIT=FALSE")
    .username("sa")
    .password("sa")
    .driver("org.h2.Driver")

  GlobalSettings.loggingSQLAndTime = LoggingSQLAndTimeSettings(singleLineMode = true, logLevel = 'warn)

  val testScenario = scenario("createTable").
    exec(
      jdbc("bar table")
        .create()
        .table("bar")
        .columns(
          column(
            name("abc"),
            dataType("INTEGER"),
            constraint("PRIMARY KEY")
          ),
          column(
            name("xyz"),
            dataType("INTEGER")
          )
        )
    )
    .repeat(10, "n") {
      exec(
        jdbc("insertion")
          .insert()
          .into("bar")
          .values("${n},${n}")
      )
    }
    .pause(1)
    .exec(
      jdbc("update")
        .update("bar")
        .set("xyz=123")
        .where("abc<5")
    )
    .pause(1)
    .exec(
      jdbc("selection")
        .select("*")
        .from("bar")
        .where("abc=4")
    )


  setUp(testScenario.inject(atOnceUsers(1)))
    .protocols(jdbcConfig)
    .assertions(global.failedRequests.count.is(0))

}
