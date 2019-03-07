
import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives
case class RouteHandler() (
   implicit val system : ActorSystem
) extends Directives {
  val route = pathPrefix("api") {
    //regular expression
    ((get | post) & path("user" / IntNumber / Segment)) { (intn , seg2) =>
      println("int " , intn , seg2)
      complete(HttpEntity(ContentTypes.`application/json`,
      """
        |{
        | "result" : true
        |}
      """.stripMargin))
    } ~ ((get | post) & path("user" / Segment / Segment)) { (seg1, seg2) =>
      println(seg1, seg2)
      complete(HttpEntity(ContentTypes.`application/json`,
        """
          |{
          | "result" : true
          | }
        """.stripMargin))
      }

    } ~ pathPrefix("admin") {
      (get & path("login")) {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<form><input type='text'></form>"))
      }
    }
}
