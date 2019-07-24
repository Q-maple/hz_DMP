package cn.DMPUtils

object DataUtils {

  //将字符串格式转化为Int
  def string2Int(str:String): Int ={
    try{
      str.toInt
    }catch {
      case e:Exception => 0
    }
  }

  //将字符串格式转化为Double
  def string2Double(dou:String): Double ={
    try{
      dou.toDouble
    }catch {
      case e : Exception => 0.0
    }
  }
}
