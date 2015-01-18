
trait LoginService {
  def login(username: String, password: String):Boolean
}

class AlwaysValidLoginService extends LoginService {
  override def login(username: String, password: String): Boolean = true
}
