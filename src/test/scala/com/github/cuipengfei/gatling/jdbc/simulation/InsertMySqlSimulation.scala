package com.github.cuipengfei.gatling.jdbc.simulation

import java.util.UUID

import com.github.cuipengfei.gatling.jdbc.Predef._
import com.github.cuipengfei.gatling.jdbc.builder.column.ColumnHelper._
import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName.parse

/**
  * Created by ronny on 10.05.17.
  */
class InsertMySqlSimulation extends Simulation {

  val mySql = new MySQLContainer(parse("mysql").withTag("8.0.22"))
  mySql.start()

  val jdbcConfig = jdbc
    .url(mySql.getJdbcUrl)
    .username(mySql.getUsername)
    .password(mySql.getPassword)
    .driver(mySql.getDriverClassName)

  val tableIdentFeeder = for (x <- 0 until 10) yield Map("tableId" -> x)

  val uniqueValuesFeeder = Iterator.continually(Map("unique" -> UUID.randomUUID()))

  after {
    mySql.stop()
  }

  val createTables = scenario("createTable").feed(tableIdentFeeder.iterator).
    exec(jdbc("create")
      .create()
      .table("bar${tableId}")
      .columns(
        column(
          name("abc"),
          dataType("VARCHAR(39)"),
          constraint("PRIMARY KEY")
        )
      )
    )

  val fillTables = feed(uniqueValuesFeeder).repeat(100, "n") {
    feed(tableIdentFeeder.iterator.toArray.random).
      exec(jdbc("insertion")
        .insert()
        .into("bar${tableId}")
        .values("'${unique}${n}'")
      )
  }

  setUp(
    createTables.inject(atOnceUsers(10)),
    scenario("fillTables").pause(5).exec(fillTables).inject(atOnceUsers(100))
  ).protocols(jdbcConfig)
    .assertions(global.successfulRequests.percent.gte(99))

}
