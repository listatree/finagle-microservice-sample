import com.twitter.finagle.http.{RequestProxy, Request}
import data.UserData

case class AuthorizedRequest(request: Request, userData: UserData) extends RequestProxy
