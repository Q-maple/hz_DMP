package cn.Tags

import org.apache.commons.lang3.{ArrayUtils, StringUtils}
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql.Row

/**
  * 关键字相关的标签
  */
object KeyWTags {

  def getKeyWord(row:Row,broadcast:Broadcast[Array[String]]):List[(String,Int)] ={
    //定义返回值
    var list = List[(String,Int)]()
    //获取stopword文件的数据和关键字进行匹配过滤
    val values = broadcast.value.toList
    val keywords = row.getAs[String]("keywords")
    val filtered = StringUtils.split("\\|").filter(t=>t.length >= 3 && t.length <= 8 && !values.contains(t))
    for (i<- filtered) {
      list :+= ("K" + i,1)
    }
    list
  }
}
