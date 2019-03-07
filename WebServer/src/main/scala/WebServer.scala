import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
object WebServer extends App{
  implicit val actorSystem = ActorSystem("web")
  implicit val dis = actorSystem.dispatcher

  //Stream
  implicit val materializer = ActorMaterializer()
  Http().bindAndHandle(RouteHandler2().route,"localhost",8080);
}
