
import akka.actor.{Actor, ActorRef, Props}

class CalculatorActor extends Actor {

  var opparser : ActorRef = _
  override def preStart(): Unit = {
    opparser = context.actorOf(Props[OperatorParserActor])
  }

  override def receive: Receive = {
    case op : plus => {
      println(op.n1 + op.n2)
    }
    case op : minus => {
      println(op.n1 - op.n2)
    }
    case op : multiple => {
      println(op.n1 * op.n2)
    }
    case op : devide => {
      if(!op.n2.equals(0)) {
        println(op.n1.toDouble / op.n2.toDouble)
      } else {
        println("cannot devide by zero")
      }
    }
    case s : String => {
      opparser ! s
    }
    case ex : InputException => {
      println( ex.Message )
    }
  }
}
