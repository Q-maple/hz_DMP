package cn.APP


object TaskUtils {

  //地域分布

  //原始请求,有效请求	,广告请求
  def getRequest(data: Tuple2[Int, Int]): List[Double] = {
    if (data._1 == 1) {
      if (data._2 == 1) {
        List(1, 0, 0)
      } else if (data._2 == 2) {
        List(1, 1, 0)
      } else if (data._2 == 3) {
        List(1, 1, 1)
      } else {
        List(0, 0, 0)
      }
    } else {
      List(0, 0, 0)
    }
  }

  //参与竞价数,竞价成功数,,广告成本,广告消费
  def getEff(data: Tuple7[Int, Int, Int, Int, Int, Double, Double]): List[Double] = {
    if (data._1 == 1 && data._2 == 1 && data._3 == 1) {
      if (data._4 == 1 && data._5 != 0) {
        List(1, 1, data._6 / 1000, data._7 / 1000)
      } else {
        List(1, 0, 0, 0)
      }
    } else {
      List(0, 0, 0, 0)
    }
  }

  //展示量,点击量
  def getDisplay(data: Tuple2[Int, Int]): List[Double] = {
    if (data._2 == 1) {
      if (data._1 == 2) {
        List(1, 0)
      } else if (data._1 == 3) {
        List(1, 1)
      } else {
        List(0, 0)
      }
    } else {
      List(0, 0)
    }
  }

  //3.2.2	终端设备


}
