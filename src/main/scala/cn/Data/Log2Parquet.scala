package cn.Data

import cn.Beans.LogBeans
import cn.DMPUtils.{DataUtils, Spark_Utils}
import cn.properties.propLoad
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{ Dataset}


object Log2Parquet {
  def main(args: Array[String]): Unit = {

    val spark = Spark_Utils.getSparkSession(propLoad.getResource("spark.master"),this.getClass.getName,("spark.serializer", "org.apache.spark.serializer.KryoSerializer"),("spark.sql.parquet.compression.codec", "snappy"))

    //读取hdfs中的文件
    val file = spark.read.textFile("hdfs://centos:9000/hz_DMP/data/2016-10-01_06_p1_invalid.1475274123982.log")
    val filtered: Dataset[String] = file.filter(t => t.split(",", -1).length >= 85)

    val rdded = filtered.rdd
    val beaned: RDD[LogBeans] = rdded.map(t => {
      val arr = t.split(",", -1)
      LogBeans(
        arr(0),
        DataUtils.string2Int(arr(1)),
        DataUtils.string2Int(arr(2)),
        DataUtils.string2Int(arr(3)),
        DataUtils.string2Int(arr(4)),
        arr(5),
        arr(6),
        DataUtils.string2Int(arr(7)),
        DataUtils.string2Int(arr(8)),
        DataUtils.string2Double(arr(9)),
        DataUtils.string2Double(arr(10)),
        arr(11),
        arr(12),
        arr(13),
        arr(14),
        arr(15),
        arr(16),
        DataUtils.string2Int(arr(17)),
        arr(18),
        arr(19),
        DataUtils.string2Int(arr(20)),
        DataUtils.string2Int(arr(21)),
        arr(22),
        arr(23),
        arr(24),
        arr(25),
        DataUtils.string2Int(arr(26)),
        arr(27),
        DataUtils.string2Int(arr(28)),
        arr(29),
        DataUtils.string2Int(arr(30)),
        DataUtils.string2Int(arr(31)),
        DataUtils.string2Int(arr(32)),
        arr(33),
        DataUtils.string2Int(arr(34)),
        DataUtils.string2Int(arr(35)),
        DataUtils.string2Int(arr(36)),
        arr(37),
        DataUtils.string2Int(arr(38)),
        DataUtils.string2Int(arr(39)),
        DataUtils.string2Double(arr(40)),
        DataUtils.string2Double(arr(41)),
        DataUtils.string2Int(arr(42)),
        arr(43),
        DataUtils.string2Double(arr(44)),
        DataUtils.string2Double(arr(45)),
        arr(46),
        arr(47),
        arr(48),
        arr(49),
        arr(50),
        arr(51),
        arr(52),
        arr(53),
        arr(54),
        arr(55),
        arr(56),
        DataUtils.string2Int(arr(57)),
        DataUtils.string2Double(arr(58)),
        DataUtils.string2Int(arr(59)),
        DataUtils.string2Int(arr(60)),
        arr(61),
        arr(62),
        arr(63),
        arr(64),
        arr(65),
        arr(66),
        arr(67),
        arr(68),
        arr(69),
        arr(70),
        arr(71),
        arr(72),
        DataUtils.string2Int(arr(73)),
        DataUtils.string2Double(arr(74)),
        DataUtils.string2Double(arr(75)),
        DataUtils.string2Double(arr(76)),
        DataUtils.string2Double(arr(77)),
        DataUtils.string2Double(arr(78)),
        arr(79),
        arr(80),
        arr(81),
        arr(82),
        arr(83),
        DataUtils.string2Int(arr(84))

      )
    })
    import spark.implicits._
    val df = beaned.toDF()
    df.write.parquet("hdfs://centos:9000/hz_DMP/etlData")
  }
}