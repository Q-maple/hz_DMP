package cn.APP

import cn.DMPUtils.{Spark_Utils, jdbcProp}
import cn.properties.propLoad
import org.apache.spark.sql.{DataFrame, SparkSession}

object Task_Media {

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = Spark_Utils.getSparkSession(propLoad.getResource("spark.master"), this.getClass.getName)
    //读取hdfs中的parquet文件
    val file: DataFrame = spark.read.parquet("hdfs://centos:9000/hz_DMP/etlData/part-00000-44216497-1dc4-4403-9646-c6105a2df423-c000.snappy.parquet")
    import spark.implicits._
    val map = spark.read.parquet("hdfs://centos:9000/hz_DMP/etlDicData/part-00000-75807429-4e38-45ac-bd6c-71611a519f92-c000.snappy.parquet")
//      .map(row => (row.getAs[String]("appname"), row.getAs[String]("appid"))).collect().toMap
//    val broadCast: Broadcast[Map[String, String]] = spark.sparkContext.broadcast(map)
//    val broadValue: Map[String, String] = broadCast.value
    //对文件创建临时表
    file.createTempView("media")
    map.createTempView("dic")
    val mediaSql = propLoad.getResource("sql.media")
    val mediaRes = spark.sql(mediaSql)
    mediaRes.write.mode("ignore").jdbc(jdbcProp.getJdbcProp._2,"mediares",jdbcProp.getJdbcProp._1)
//    mediaRes.show()
  }
}
