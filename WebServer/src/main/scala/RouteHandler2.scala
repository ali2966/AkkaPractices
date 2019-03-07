import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives

case class RouteHandler2 () (
  implicit val system : ActorSystem
) extends Directives {
  val route = pathPrefix("user") {
    ((get | post) & path("register" / Segment / Segment / Segment )) { (name, username , password) =>
      var Response: String = ""
      Database.Register(name,username,password) match {
        case Right(user) => Response = "Register done! name : " + user.name + "username : " + user.username
        case Left(RegisterFailStatus.UserAllReadyExists) => Response = "User already exists with this username!"
      }
      complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, Response))
    } ~
    ((get | post) & path( "login" / Segment / Segment )) { (username , password) => {
        var Response: String = ""
        Database.login(username, password) match {
          case Right(token) => Response = "login success! your token is : " + token
          case Left(LoginFailStatus.UserNotExists) => Response = "user does not exists"
          case Left(LoginFailStatus.PasswordNotCorrect) => Response = "Password Not Correct"
        }
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, Response))
      }
    } ~
    ((get | post) & path("getme" / Segment)) { token =>
      var Response : String = ""
      if(Database.isTokenValid(token)) {
        val user = Database.UserWithToken(token)
        Response = "your name is : " + user.name
      }
      else {
        Response = "your token is not valid"
      }

      complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`,Response))
    }
  }
}
