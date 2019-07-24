package cn.Tags

import org.apache.spark.sql.Row


/**
  * 设备相关的标签
  */
object DevTags {

  //操作系统标签
  def getClient(row:Row): List[(String,String)] = {

    //定义返回变量
    var list = List[(String,String)]()
    val client = row.getAs[Int]("client")
    client match {
      case 1 => list :+= (client + " Android", "D0001000" + client)
      case 2 => list :+= (client + " IOS", "D0001000" + client)
      case 3 => list :+= (client + " WP", "D0001000" + client)
      case _ => list :+= ("_ 其它", "D00010004")
    }
    list
  }

  //联网方式标签
  def connType(row:Row) :List[(String,String)] ={

    //定义返回值变量
    var list = List[(String,String)]()
    val netID = row.getAs[Int]("networkmannerid")
    val netName = row.getAs[String]("networkmannername")
    netID match {
      case 1 => list :+= (netName,"D00020004")
      case 2 => list :+= (netName,"D00020003")
      case 5 => list :+= (netName,"D00020002")
      case 3 => list :+= (netName,"D00020001")
      case _ => list :+= ("_","D00020005")
    }
    list
  }

  //设备运营商
  def getPname(row:Row):List[(String,String)] ={
    //定义返回变量
    var list = List[(String,String)]()
    val pid = row.getAs[Int]("ispid")
    val pname = row.getAs[String]("ispname")
    pid match {
      case 1 => list :+= (pname,"D0003000" + pid)
      case 2 => list :+= (pname,"D0003000" + pid)
      case 3 => list :+= (pname,"D0003000" + pid)
      case _ => list :+= ("_","D00030004")
    }
    list
  }
}
