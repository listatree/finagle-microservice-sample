
import com.twitter.finagle.Service
import com.twitter.finagle.http.Response
import com.twitter.util.Future
import org.jboss.netty.handler.codec.http.HttpResponseStatus
import reports.ReportProcessor

class ProcessReportHandler(reportProcessor: ReportProcessor) extends Service[AuthorizedRequest, Response] {

  val processReportUrl = "/report/([0-9]+)/process".r

  override def apply(req: AuthorizedRequest): Future[Response] = req.request.path match {
    case processReportUrl(processId) =>
      reportProcessor.processReport(processId.toInt) map { _ =>
        val response = Response(req.request.getProtocolVersion(), HttpResponseStatus.OK)
        response.setContentTypeJson()
        response.setContentString(
          s"""
            {
              "processId": $processId,
              "processed": true
            }
          """)

        response
      } rescue {
        case _ => Future.value(Response(req.request.getProtocolVersion(), HttpResponseStatus.INTERNAL_SERVER_ERROR))
      }
    case _ => Future.value(Response(req.request.getProtocolVersion(), HttpResponseStatus.NOT_FOUND))
  }

}
