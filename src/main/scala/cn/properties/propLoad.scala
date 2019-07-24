package cn.properties

import java.util.Properties

object propLoad {

  val prop = new Properties()
  try {
    val jdbc = propLoad.getClass.getClassLoader.getResourceAsStream("jdbc.properties")
    val sparkprop = propLoad.getClass.getClassLoader.getResourceAsStream("spark.properties")
    val sql = propLoad.getClass.getClassLoader.getResourceAsStream("sql.properties")
    prop.load(jdbc)
    prop.load(sparkprop)
    prop.load(sql)
  }catch {
    case e:Exception => e.printStackTrace()
  }

  def getResource(key:String): String ={
    prop.getProperty(key)
  }
}
