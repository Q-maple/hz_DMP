package cn.DMPUtils

import java.util.Properties

import cn.properties.propLoad

object jdbcProp {


  def getJdbcProp:(Properties,String) ={
    val prop = new Properties()

    prop.put("user",propLoad.getResource("jdbc.user"))
    prop.put("password",propLoad.getResource("jdbc.password"))
    prop.put("driver",propLoad.getResource("jdbc.driver"))
    val url = propLoad.getResource("jdbc.url")

    (prop,url)
  }
}