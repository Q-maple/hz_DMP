package cn.APP

import cn.DMPUtils.Spark_Utils
import cn.properties.propLoad
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object Task_Area {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = Spark_Utils.getSparkSession(propLoad.getResource("spark.master"), this.getClass.getName)
    //读取hdfs中的parquet文件
    val file: DataFrame = spark.read.parquet("hdfs://centos:9000/hz_DMP/etlData/part-00000-44216497-1dc4-4403-9646-c6105a2df423-c000.snappy.parquet")
    import spark.implicits._
    val maped = file.map(row => {
      val provincename = row.getAs[String]("provincename")
      val cityname = row.getAs[String]("cityname")
      val requestmode = row.getAs[Int]("requestmode")
      val processnode = row.getAs[Int]("processnode")
      val iseffective = row.getAs[Int]("iseffective")
      val isbilling = row.getAs[Int]("isbilling")
      val isbid = row.getAs[Int]("isbid")
      val iswin = row.getAs[Int]("iswin")
      val adorderid = row.getAs[Int]("adorderid")
      val winprice = row.getAs[Double]("winprice")
      val adpayment = row.getAs[Double]("adpayment")
      (
        provincename,
        cityname,
        requestmode,
        processnode,
        iseffective,
        isbilling,
        isbid,
        iswin,
        adorderid,
        winprice,
        adpayment
      )
    }).cache()

    //区域分布业务
    val result1: Dataset[((String, String), List[Double])] = maped.map(t => {
      val request = TaskUtils.getRequest((t._3, t._4))
      val effective = TaskUtils.getEff((t._5, t._6, t._7, t._8, t._9, t._10, t._11))
      val displayed = TaskUtils.getDisplay((t._3, t._5))
      ((t._1, t._2), request ++ effective ++ displayed)
    })
    val devRes: RDD[((String, String), List[Double])] = result1.rdd.reduceByKey((list1, list2)=>list1.zip(list2).map(t=>t._1+t._2))
    devRes.foreach(t=>println(t._1._1 + "," + t._1._2 + "," + t._2.mkString(",")))

  }

}
