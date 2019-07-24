package cn.Task

import cn.DMPUtils.{Spark_Utils, jdbcProp}
import cn.properties.propLoad
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * 将etl后的数据保存到mysql一份和存储为json格式一份
  */
object DMP_Task {

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = Spark_Utils.getSparkSession(propLoad.getResource("spark.master"), this.getClass.getName)
    //读取hdfs中的parquet文件,将其生成为临时视图
    val file: DataFrame = spark.read.parquet("hdfs://centos:9000/hz_DMP/etlData/part-00000-44216497-1dc4-4403-9646-c6105a2df423-c000.snappy.parquet")
    file.createTempView("odsData")
    //业务sql
    val sql = "select count(*) orderNum,provincename,cityname from odsData group by provincename,cityname"
    val res1 = spark.sql(sql)

    //将数据写入mysql
    res1.write.mode("ignore").jdbc(jdbcProp.getJdbcProp._2, "task01", jdbcProp.getJdbcProp._1)

    //json
    res1.write.partitionBy("provincename", "cityname").json("F:\\08_project\\Spark_DMP\\res")

  }
}
