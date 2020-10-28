package com.github.cuipengfei.gatling.jdbc.action

import io.gatling.commons.util.Clock
import io.gatling.commons.validation.Success
import io.gatling.core.action.Action
import io.gatling.core.session.{Expression, Session}
import io.gatling.core.stats.StatsEngine
import scalikejdbc.{DB, SQL}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class JdbcUpdateAction[T](requestName: Expression[String],
                               table: Expression[String],
                               values: Expression[String],
                               clock: Clock,
                               statsEngine: StatsEngine,
                               next: Action) extends JdbcAction {

  override def name: String = genName("jdbcUpdate")

  override def execute(session: Session): Unit = {
    val start = clock.nowMillis
    val validatedTableName = table.apply(session)
    val validatedValues = values.apply(session)

    val sqlString = (validatedTableName, validatedValues) match {
      case (Success(table), Success(values)) => s"UPDATE $table SET $values"
      case _ => throw new IllegalArgumentException
    }

    val future: Future[Boolean] = Future {
      DB autoCommit { implicit session =>
        SQL(sqlString).execute().apply()
      }
    }

    future.onComplete { result =>
      next ! log(start, clock.nowMillis, result, requestName, session, statsEngine)
    }
  }
}
