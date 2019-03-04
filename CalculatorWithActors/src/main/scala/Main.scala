
import akka.actor.{ActorSystem, Props}
object Main extends App {

  println("enter your expression ... (example : 5 + 2)")

 val actorSystem = ActorSystem("godfather")

 val dispatcher = actorSystem.dispatcher

 val calculator =  actorSystem.actorOf(Props[CalculatorActor])

 //calculator ! plus(3,6)
 //calculator ! minus(8,4)
 //calculator ! multiple(2,6)
 //calculator ! devide(6,3)
  var control : Boolean = true
  while(control)
  {
    var order : String = readLine()
    if(order.equals("exit")){
      control = false
    }
    else {
      calculator ! order
    }
  }


}
