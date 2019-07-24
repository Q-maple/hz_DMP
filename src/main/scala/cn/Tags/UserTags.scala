package cn.Tags

import cn.DMPUtils.{Spark_Utils, TagsUtils}
import cn.properties.propLoad
import org.apache.spark.sql.{DataFrame, SparkSession}

object UserTags {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = Spark_Utils.getSparkSession(propLoad.getResource("spark.master"), this.getClass.getName)
    import spark.implicits._
    //读取hdfs中的parquet文件
    val file: DataFrame = spark.read.parquet("hdfs://centos:9000/hz_DMP/etlData/part-00000-44216497-1dc4-4403-9646-c6105a2df423-c000.snappy.parquet")
    //广播appname的文件
    val map = spark.read.textFile("hdfs://centos:9000/hz_DMP/etlDicData/part-00000-75807429-4e38-45ac-bd6c-71611a519f92-c000.snappy.parquet")
      .map(t => (t.split("\t")(4), t.split("\t")(1))).collect().toMap
    //读取暂停文件并且进行广播
    val stopWord = spark.read.textFile("F:\\08_project\\Spark_DMP\\stopwords.txt").collect()
    val broadcast = spark.sparkContext.broadcast(map)
    val stopBoard = spark.sparkContext.broadcast(stopWord)
    //过滤删除掉没有唯一id标识 的数据

    file.filter(TagsUtils.filtered)
      .map(row => {
        //获取出用户唯一id标示
        val userID = TagsUtils.filterID(row)
        //广告标签 类型+名称
        val adTypeName = ADTags.getAdTypeName(row)
        //App的访问标签
        AppTags.getAppIdName(row, broadcast)
        //渠道标签
        val platform = ADTags.getPlatform(row)
        //设备相关标签
        val connType = DevTags.connType(row)
        val client = DevTags.getClient(row)
        val pName = DevTags.getPname(row)
        //关键字标签
        val keyWord = KeyWTags.getKeyWord(row,stopBoard)
        //地域标签
        val province = AreaTags.getProvince(row)
        val city = AreaTags.getCity(row)
        (userID,adTypeName,platform,connType,client,pName,keyWord,province,city)
      })
  }
}
