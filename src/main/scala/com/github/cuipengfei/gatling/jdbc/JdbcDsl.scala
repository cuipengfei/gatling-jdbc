package com.github.cuipengfei.gatling.jdbc

import com.github.cuipengfei.gatling.jdbc.builder.JdbcActionBuilderBase
import com.github.cuipengfei.gatling.jdbc.check.JdbcCheckSupport
import com.github.cuipengfei.gatling.jdbc.protocol.{JdbcProtocol, JdbcProtocolBuilder, JdbcProtocolBuilderBase, JdbcProtocolBuilderConnectionPoolSettingsStep}
import io.gatling.core.session.Expression

import scala.language.implicitConversions

/**
  * Created by ronny on 10.05.17.
  */
trait JdbcDsl extends JdbcCheckSupport {

  val jdbc = JdbcProtocolBuilderBase

  def jdbc(requestName: Expression[String]) = JdbcActionBuilderBase(requestName)

  implicit def jdbcProtocolBuilder2JdbcProtocol(protocolBuilder: JdbcProtocolBuilder): JdbcProtocol = protocolBuilder.build

  implicit def jdbcProtocolBuilderConnectionPoolSettingsStep2JdbcProtocol(protocolBuilder: JdbcProtocolBuilderConnectionPoolSettingsStep): JdbcProtocol = protocolBuilder.build

}
