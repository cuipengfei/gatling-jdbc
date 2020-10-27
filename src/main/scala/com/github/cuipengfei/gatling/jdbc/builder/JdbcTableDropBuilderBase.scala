package com.github.cuipengfei.gatling.jdbc.builder

import com.github.cuipengfei.gatling.jdbc.action.JdbcTableDroppingActionBuilder
import io.gatling.core.session.Expression

/**
  * Created by ronny on 11.05.17.
  */
case class JdbcTableDropBuilderBase(requestName: Expression[String]) {

  def table(name: Expression[String]) = JdbcTableDroppingActionBuilder(requestName, name)

}
