package cn.Data

import cn.DMPUtils.Spark_Utils
import cn.properties.propLoad
import org.apache.spark.sql.DataFrame

object Dictionary {
  def main(args: Array[String]): Unit = {

    val spark = Spark_Utils.getSparkSession(propLoad.getResource("spark.master"),this.getClass.getName,("spark.serializer", "org.apache.spark.serializer.KryoSerializer"),("spark.sql.parquet.compression.codec", "snappy"))

    //读取hdfs中的文件
    val file = spark.read.textFile("hdfs://centos:9000/hz_DMP/dictionary/app_dict.txt")
    val map = file.rdd.map(t=>t.split("\t",-1)).filter(_.length >= 5).map(t=>(t(4),t(1)))
    import spark.implicits._
    val df: DataFrame = map.toDF("appid","appname")
    df.write.parquet("hdfs://centos:9000/hz_DMP/etlDicData")
  }
}
