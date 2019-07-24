package cn.DMPUtils
import java.sql.{Connection, DriverManager}
import java.util

object JDBCPool {

    //最大连接数
    private val max = 50
    //每次产生的连接数
    private val connectNums = 10
    //创建连接池
    private val connectPool = new util.LinkedList[Connection]()
    //当前连接数量
    private var connectedNums = 0


    def getConnect : Connection = {
      AnyRef.synchronized(
        if (connectPool.isEmpty) {
          //获取驱动
          getDriver
          for (i <- 1 to 10) {
            val connections: Connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/streaming","root","123456")
            connectPool.push(connections)
            connectedNums += 1
          }
        })
      connectPool.poll()
    }

    //释放连接
    def release(conn:Connection): Unit = {
      connectPool.push(conn)
    }

    private def getDriver: Unit = {
      if (connectedNums > max) {
        println("无可用线程")
        Thread.sleep(2000)
        getDriver
      }else {
        Class.forName("com.mysql.jdbc.Driver")
      }

  }

}
