package cn.APP

import cn.DMPUtils.{Spark_Utils, jdbcProp}
import cn.properties.propLoad
import org.apache.spark.sql.{DataFrame, SparkSession}


/**
  * 终端设备
  */
object Task_Dev {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = Spark_Utils.getSparkSession(propLoad.getResource("spark.master"), this.getClass.getName)
    //读取hdfs中的parquet文件
    val file: DataFrame = spark.read.parquet("hdfs://centos:9000/hz_DMP/etlData/part-00000-44216497-1dc4-4403-9646-c6105a2df423-c000.snappy.parquet")
    //对文件创建临时表
    file.createTempView("devs")
    //运营
    val sql1 = propLoad.getResource("sql.dev_operation")
    //
    val sql2 = propLoad.getResource("sql.dev_nettype")
    val sql3 = propLoad.getResource("sql.dev_dev")
    val sql4 = propLoad.getResource("sql.dev_system")
    val res1 = spark.sql(sql1)
    val res2 = spark.sql(sql2)
    val res3 = spark.sql(sql3)
    val res4 = spark.sql(sql4)
    res1.write.mode("ignore").jdbc(jdbcProp.getJdbcProp._2,"operationRes",jdbcProp.getJdbcProp._1)
    res2.write.mode("ignore").jdbc(jdbcProp.getJdbcProp._2,"nettypeRes",jdbcProp.getJdbcProp._1)
    res3.write.mode("ignore").jdbc(jdbcProp.getJdbcProp._2,"devRes",jdbcProp.getJdbcProp._1)
    res4.write.mode("ignore").jdbc(jdbcProp.getJdbcProp._2,"systemRes",jdbcProp.getJdbcProp._1)
   }
}