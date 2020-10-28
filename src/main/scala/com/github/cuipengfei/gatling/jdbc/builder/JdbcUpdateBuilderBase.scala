package com.github.cuipengfei.gatling.jdbc.builder

import com.github.cuipengfei.gatling.jdbc.action.JdbcUpdateActionBuilder
import io.gatling.core.session.Expression

case class JdbcUpdateBuilderBase(requestName: Expression[String], table: Expression[String]) {

  def set(values: Expression[String]) = JdbcUpdateActionBuilder(requestName, table, values)

}
