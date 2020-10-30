package com.github.cuipengfei.gatling.jdbc.protocol

import io.gatling.core.protocol.ProtocolComponents
import io.gatling.core.session.Session

/**
  * Created by ronny on 10.05.17.
  */
case class JdbcComponents(protocol: JdbcProtocol) extends ProtocolComponents {

  override def onStart: Session => Session = Session.Identity

  override def onExit: Session => Unit = ProtocolComponents.NoopOnExit

}
