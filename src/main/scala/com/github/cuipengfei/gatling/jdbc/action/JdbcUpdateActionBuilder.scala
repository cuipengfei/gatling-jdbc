package com.github.cuipengfei.gatling.jdbc.action

import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.session.Expression
import io.gatling.core.structure.ScenarioContext

case class JdbcUpdateActionBuilder(requestName: Expression[String], table: Expression[String], values: Expression[String])
  extends ActionBuilder {

  override def build(ctx: ScenarioContext, next: Action): Action = {
    val statsEngine = ctx.coreComponents.statsEngine
    val clock = ctx.coreComponents.clock
    JdbcUpdateAction(requestName, table, values, clock, statsEngine, next)
  }

}
