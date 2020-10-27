package com.github.cuipengfei.gatling.jdbc.builder

import io.gatling.core.session.Expression

/**
  * Created by ronny on 10.05.17.
  */
case class JdbcActionBuilderBase(requestName: Expression[String]) {

  def create() = JdbcTableCreationBuilderBase(requestName)

  def insert() = JdbcInsertionBuilderBase(requestName)

  def select(what: Expression[String]) = JdbcSelectionBuilderBase(requestName, what)

  def drop() = JdbcTableDropBuilderBase(requestName)

  def delete() = JdbcDeletionBuilderBase(requestName)
}
