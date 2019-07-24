package cn.Tags

import org.apache.spark.sql.Row

/**
  * 广告相关的标签
  */
object ADTags {

  //广告类型 名称标签
  def getAdTypeName(row:Row): List[(String,Int)] = {

    //定义返回变量
    var list = List[(String,Int)]()

    //匹配广告类型
    row.getAs[Int]("adspacetype") match {
      case v if v > 0 && v < 9 => list :+= ("LC0" + v, 1)
      case v if v > 9 => list :+= ("LC" + v,1)
    }
    //广告名称
    list :+= ("LN" + row.getAs[String]("adspacetypename"), 1)

    list
  }

  //渠道标签
  def getPlatform(row:Row): List[(String,Int)] = {

    //定义返回变量
    var list = List[(String,Int)]()
    list :+= ("CN" + row.getAs[String]("adplatformproviderid"), 1)
    list
  }
}
