package cn.DMPUtils

import org.apache.spark.sql.SparkSession

object Spark_Utils {

  def getSparkSession(mas:String,name:String,conf1:Tuple2[String,String],conf2:Tuple2[String,String]): SparkSession ={
    val sc = SparkSession.builder().master(mas).appName(name).config(conf1._1,conf1._2).config(conf2._1,conf2._2).getOrCreate()
    sc
  }

  def getSparkSession(mas:String,name:String): SparkSession ={
    val sc = SparkSession.builder().master(mas).appName(name).config("spark.debug.maxToStringFields","100").getOrCreate()
    sc
  }
}