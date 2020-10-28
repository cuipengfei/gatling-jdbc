package com.github.cuipengfei.gatling.jdbc.action

import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.session.Expression
import io.gatling.core.structure.ScenarioContext

case class JdbcUpdateActionBuilder(requestName: Expression[String],
                                   table: Expression[String],
                                   values: Expression[String]) extends ActionBuilder {

  def where(where: Expression[String]) = JdbcUpdateWithWhereActionBuilder(requestName, table, values, where)

  override def build(ctx: ScenarioContext, next: Action): Action = {
    val statsEngine = ctx.coreComponents.statsEngine
    val clock = ctx.coreComponents.clock
    JdbcUpdateAction(requestName, table, values, None, clock, statsEngine, next)
  }
}

case class JdbcUpdateWithWhereActionBuilder(requestName: Expression[String],
                                            table: Expression[String],
                                            values: Expression[String],
                                            where: Expression[String]) extends ActionBuilder {

  override def build(ctx: ScenarioContext, next: Action): Action = {
    val statsEngine = ctx.coreComponents.statsEngine
    val clock = ctx.coreComponents.clock
    JdbcUpdateAction(requestName, table, values, Some(where), clock, statsEngine, next)
  }

}
