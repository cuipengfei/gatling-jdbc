package com.github.cuipengfei.gatling.jdbc.builder

import com.github.cuipengfei.gatling.jdbc.action.JdbcTableCreationActionBuilder
import com.github.cuipengfei.gatling.jdbc.builder.column.ColumnDefinition
import io.gatling.core.session.Expression

/**
  * Created by ronny on 10.05.17.
  */
case class JdbcTableCreationBuilderBase(requestName: Expression[String]) {

  def table(name: Expression[String]) = JdbcTableCreationColumnsStep(requestName, name)

}

case class JdbcTableCreationColumnsStep(requestName: Expression[String], tableName: Expression[String]) {

  def columns(column: ColumnDefinition, moreColumns: ColumnDefinition*) = JdbcTableCreationActionBuilder(requestName, tableName, column +: moreColumns)

}
