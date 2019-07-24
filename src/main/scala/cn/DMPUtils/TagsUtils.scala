package cn.DMPUtils

import org.apache.commons.lang3.StringUtils
import org.apache.spark.sql.Row

object TagsUtils {

   val filtered:String =
    """
      |imei != "" or mac != "" or idfa != "" or openudid != "" or androidid != "" or
      |imeimd5 != "" or macmd5 != "" or idfamd5 != "" or openudidmd5 != "" or androididmd5 != "" or
      |imeisha1 != "" or macsha1 != "" or idfasha1 != "" or openudidsha1 != "" or androididsha1 != ""
    """.stripMargin

  def filterID(row:Row): String ={
    row match {
      case v if StringUtils.isNoneBlank(row.getAs[String]("imei")) => row.getAs[String]("imei")
      case v if StringUtils.isNoneBlank(row.getAs[String]("mac")) => row.getAs[String]("mac")
      case v if StringUtils.isNoneBlank(row.getAs[String]("idfa")) => row.getAs[String]("idfa")
      case v if StringUtils.isNoneBlank(row.getAs[String]("openudid")) => row.getAs[String]("openudid")
      case v if StringUtils.isNoneBlank(row.getAs[String]("androidid")) => row.getAs[String]("androidid")
      case v if StringUtils.isNoneBlank(row.getAs[String]("imeimd5")) => row.getAs[String]("imeimd5")
      case v if StringUtils.isNoneBlank(row.getAs[String]("macmd5")) => row.getAs[String]("macmd5")
      case v if StringUtils.isNoneBlank(row.getAs[String]("idfamd5")) => row.getAs[String]("idfamd5")
      case v if StringUtils.isNoneBlank(row.getAs[String]("openudidmd5")) => row.getAs[String]("openudidmd5")
      case v if StringUtils.isNoneBlank(row.getAs[String]("androididmd5")) => row.getAs[String]("androididmd5")
      case v if StringUtils.isNoneBlank(row.getAs[String]("imeisha1")) => row.getAs[String]("imeisha1")
      case v if StringUtils.isNoneBlank(row.getAs[String]("macsha1")) => row.getAs[String]("macsha1")
      case v if StringUtils.isNoneBlank(row.getAs[String]("idfasha1")) => row.getAs[String]("idfasha1")
      case v if StringUtils.isNoneBlank(row.getAs[String]("openudidsha1")) => row.getAs[String]("openudidsha1")
      case v if StringUtils.isNoneBlank(row.getAs[String]("androididsha1")) => row.getAs[String]("androididsha1")
    }
  }

}
