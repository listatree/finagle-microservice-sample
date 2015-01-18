import com.twitter.finagle.{Service, Filter}
import com.twitter.finagle.http.{Response, Request}
import com.twitter.util.Future
import data.UserData
import org.jboss.netty.handler.codec.http.HttpHeaders.Names
import org.jboss.netty.handler.codec.http.HttpResponseStatus

class AuthenticationFilter(loginService: LoginService) extends Filter[Request, Response, AuthorizedRequest, Response] {

  override def apply(request: Request, continue: Service[AuthorizedRequest, Response]): Future[Response] = {
    if(request.headers().contains(Names.AUTHORIZATION)) {
      //for simplicity, we assume the Authorization request header is in the format "username:password"
      request.headers().get(Names.AUTHORIZATION).split(":") match {
        case Array(username, password) =>
          if(loginService.login(username, password)) {
            val authorizedRequest = AuthorizedRequest(request, UserData(username, List("guest")))
            continue(authorizedRequest) //continue to the next service/filter
          } else unauthorized(request)
        case _ => unauthorized(request)
      }
    } else unauthorized(request)
  }

  def unauthorized(request: Request): Future[Response] =
    Future.value(Response(request.getProtocolVersion(), HttpResponseStatus.UNAUTHORIZED))
}
