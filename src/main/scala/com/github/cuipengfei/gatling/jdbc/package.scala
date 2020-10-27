package com.github.cuipengfei.gatling

import io.gatling.core.check.Check

/**
  * Created by ronny on 15.05.17.
  */
package object jdbc {

  type JdbcCheck[T] = Check[List[T]]

}
