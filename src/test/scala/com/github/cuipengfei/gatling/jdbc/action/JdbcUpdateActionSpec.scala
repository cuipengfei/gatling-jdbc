package com.github.cuipengfei.gatling.jdbc.action

import io.gatling.commons.stats.{KO, OK}
import io.gatling.commons.util.DefaultClock
import io.gatling.core.Predef._
import io.gatling.core.stats.writer.ResponseMessage
import scalikejdbc._

class JdbcUpdateActionSpec extends JdbcActionSpec {

  private val clock = new DefaultClock

  "JdbcUpdateAction" should "use the request name in the log message" in {
    DB autoCommit { implicit session =>
      sql"""CREATE TABLE test_table(id INTEGER PRIMARY KEY,abc INTEGER);""".execute().apply()
    }
    val requestName = "simulation"
    val latchAction = BlockingLatchAction()
    val action = JdbcUpdateAction(requestName, "test_table", "abc=123", None, clock, statsEngine, latchAction)

    action.execute(session)

    waitForLatch(latchAction)
    statsEngine.dataWriterMsg should have length 1
    statsEngine.dataWriterMsg.head(session).toOption.get.asInstanceOf[ResponseMessage].name should equal(requestName)
  }

  it should "add the exception message to the log message" in {
    val latchAction = BlockingLatchAction()
    val action = JdbcUpdateAction("simulation", "not_a_table", "abc=1", None, clock, statsEngine, latchAction)

    action.execute(session)

    waitForLatch(latchAction)
    statsEngine.dataWriterMsg should have length 1
    statsEngine.dataWriterMsg.head(session).toOption.get.asInstanceOf[ResponseMessage].message shouldNot be(empty)
  }

  it should "update all values without where clause" in {
    DB autoCommit { implicit session =>
      sql"""CREATE TABLE table1(id INTEGER PRIMARY KEY,abc INTEGER ); INSERT INTO table1 VALUES (1,1);INSERT INTO table1 VALUES (2,2)"""
        .execute().apply()
    }
    val latchAction = BlockingLatchAction()
    val action = JdbcUpdateAction("request", "table1", "abc=666", None, clock, statsEngine, latchAction)

    action.execute(session)

    waitForLatch(latchAction)
    statsEngine.dataWriterMsg should have length 1
    statsEngine.dataWriterMsg.head(session).toOption.get.asInstanceOf[ResponseMessage].status should equal(OK)
  }

  it should "update values specified by where clause" in {
    DB autoCommit { implicit session =>
      sql"""CREATE TABLE table2(id INTEGER PRIMARY KEY,abc INTEGER ); INSERT INTO table2 VALUES (1,1);INSERT INTO table2 VALUES (2,2)"""
        .execute().apply()
    }
    val latchAction = BlockingLatchAction()
    val action = JdbcUpdateAction("request", "table2", "abc=666", Some("id<2"), clock, statsEngine, latchAction)

    action.execute(session)

    waitForLatch(latchAction)
    statsEngine.dataWriterMsg should have length 1
    statsEngine.dataWriterMsg.head(session).toOption.get.asInstanceOf[ResponseMessage].status should equal(OK)
  }

  it should "log an KO value after unsuccessful update" in {
    val latchAction = BlockingLatchAction()
    val action = JdbcUpdateAction("request", "not_a_table", "failure", None, clock, statsEngine, latchAction)

    action.execute(session)

    waitForLatch(latchAction)
    statsEngine.dataWriterMsg should have length 1
    statsEngine.dataWriterMsg.head(session).toOption.get.asInstanceOf[ResponseMessage].status should equal(KO)
  }

  it should "throw an IAE when it cannot evaluate the table expression" in {
    val action = JdbcUpdateAction("request", "${tableName}", "abc=1", None, clock, statsEngine, next)

    an[IllegalArgumentException] should be thrownBy action.execute(session)
  }

  it should "throw an IAE when it cannot evaluate the values expression" in {
    val action = JdbcUpdateAction("request", "tableName", "${values}", None, clock, statsEngine, next)

    an[IllegalArgumentException] should be thrownBy action.execute(session)
  }

  it should "throw an IAE when it cannot evaluate the where expression" in {
    val action = JdbcUpdateAction("request", "tableName", "abc=1", Some("${where}"), clock, statsEngine, next)

    an[IllegalArgumentException] should be thrownBy action.execute(session)
  }

  it should "pass the session to the next action" in {
    DB autoCommit { implicit session =>
      sql"""CREATE TABLE insert_next(id INTEGER PRIMARY KEY )""".execute().apply()
    }
    val nextAction = NextAction(session)
    val action = JdbcUpdateAction("request", "test_table", "abc=123", None, clock, statsEngine, nextAction)

    action.execute(session)

    waitForLatch(nextAction)
    nextAction.called should be(true)
  }

}
