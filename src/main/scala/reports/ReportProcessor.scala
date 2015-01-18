package reports

import java.util.concurrent.TimeUnit

import com.twitter.util.{Timer, Duration, Future}


trait ReportProcessor {

  def processReport(id: Int): Future[Unit]
}

class FakeReportProcessor extends ReportProcessor {

  implicit val timer = Timer.Nil

  override def processReport(id: Int): Future[Unit] = {
    //fake the processing of the report by returning a future that completes after 2 seconds
    Future.sleep(Duration(2, TimeUnit.SECONDS))
  }
}
