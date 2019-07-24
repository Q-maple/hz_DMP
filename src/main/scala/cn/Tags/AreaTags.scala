package cn.Tags

import org.apache.spark.sql.Row

/**
  * 地域相关的标签
  */
object AreaTags {

  //获取省份标签
  def getProvince(row:Row): List[(String,Int)] = {
    var list = List[(String,Int)]()
    list :+= ("ZP" + row.getAs[String]("provincename"), 1)
    list
  }
  //获取地级市标签
  def getCity(row:Row): List[(String,Int)] = {
    var list = List[(String,Int)]()
    list :+= ("ZC" + row.getAs[String]("provincename"), 1)
    list
  }
}
