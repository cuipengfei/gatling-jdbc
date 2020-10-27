package com.github.cuipengfei.gatling.jdbc.check

import com.github.cuipengfei.gatling.jdbc.JdbcCheck
import io.gatling.core.action.builder.ActionBuilder

import scala.collection.mutable.ArrayBuffer

/**
  * Created by ronny on 15.05.17.
  */
trait JdbcCheckActionBuilder[T] extends ActionBuilder {

  protected val checks: ArrayBuffer[JdbcCheck[T]] = ArrayBuffer.empty

  def check(check: JdbcCheck[T]): ActionBuilder = {
    checks += check
    this
  }

}
