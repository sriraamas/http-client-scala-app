package example

import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpGet
import org.apache.http.config.SocketConfig
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import scala.util.Try
import scala.util.chaining._

object Hello {

  def main(args: Array[String]): Unit = {
    val connectTimeout = 200
    val socketTimeout = 1000
    val connectionRequestTimeout = 100
    val maxConnections = 100
    val requestConfig = RequestConfig
      .custom()
      .setConnectTimeout(connectTimeout)
      .setSocketTimeout(socketTimeout)
      .setConnectionRequestTimeout(connectionRequestTimeout)
      .build()

    val client = HttpClientBuilder
      .create()
      .setMaxConnPerRoute(maxConnections)
      .setMaxConnTotal(maxConnections)
      .setDefaultRequestConfig(requestConfig)
      .disableAutomaticRetries()
      .setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(socketTimeout).build())
      .build()

    val request = new HttpGet("http://localhost:8099")

    for (i <- 1 to 100) {
      Try {
        client.execute(request).tap(r => EntityUtils.consume(r.getEntity)).getStatusLine.getStatusCode
      }.map { status =>
        println("statusCode" + status)
      }.failed
        .map { e =>
          println(e.getMessage)
        }
      Thread.sleep(400)
    }
  }

}

trait Greeting {
  lazy val greeting: String = "hello"
}
