package cn.Tags

import org.apache.commons.lang.StringUtils
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql.Row

/**
  * app访问标签
  */

object AppTags {

  //获取访问的app的名称
  def getAppIdName(row:Row,broadcast:Broadcast[Map[String, String]]): List[(String,Int)] = {
    //定义返回值
    var list = List[(String,Int)]()
    //获取appid和appname
    val appid = row.getAs[String]("appid")
    val appname = row.getAs[String]("appname")
    if (StringUtils.isNotBlank(appid)) {
      if (StringUtils.isNotBlank(appname)) {
        list :+= ("APP" + appname, 1)
      }else {
        list :+= ("APP" + broadcast.value.getOrElse(appid,appid), 1)
      }
    }else {
      list :+= ("APP" + appname, 1)
    }
    list
  }
}
