import java.net.InetSocketAddress

import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.{Http, Request, RichHttp}
import com.twitter.finagle.stats.MetricsExporter
import reports.FakeReportProcessor

object MicroService extends App {

  val loginService = new AlwaysValidLoginService()
  val reportProcessor = new FakeReportProcessor()
  val authenticateUser = new AuthenticationFilter(loginService)
  val processReport = new ProcessReportHandler(reportProcessor)
  val metricsExporter = new MetricsExporter()

  //setup service chain
  val serviceChain = authenticateUser andThen processReport

  //HTTP endpoint
  val socketAddress = new InetSocketAddress(8080)
  val server = ServerBuilder()
    .codec(new RichHttp[Request](Http()))
    .bindTo(socketAddress)
    .name("HTTP endpoint")
    .build(serviceChain)

  println("microservice started")
}
