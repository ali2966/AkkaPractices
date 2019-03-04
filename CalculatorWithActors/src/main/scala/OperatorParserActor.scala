
import java.lang.Exception

import akka.actor.Actor

class OperatorParserActor extends Actor {


  override def receive: Receive = {

    case expression : String => {
      try {
        var stringOperands = expression.split(Array('+' , '-', '*', '/')).map(_.trim())
        var IntegerOperands = stringOperands.map((i: String) => i.toDouble)

        var operator = getOperator(expression)
        operator match {
          case '+' => {
            sender() ! plus( IntegerOperands(0) , IntegerOperands(1) )
          }
          case '-' => {
            sender() ! minus( IntegerOperands(0) , IntegerOperands(1) )
          }
          case '*' => {
            sender() ! multiple( IntegerOperands(0) , IntegerOperands(1) )
          }
          case '/' => {
            sender() ! devide( IntegerOperands(0) , IntegerOperands(1) )
          }
        }
      }
      catch{
        case ex =>
        sender() ! InputException("entered expression incorrect , check and retry")
      }
    }
  }
  def getOperator(s : String): Char = {
    if(s.contains('+')) {
      return '+'
    } else if(s.contains('-')) {
      return '-'
    } else if(s.contains('*')) {
      return '*'
    } else if(s.contains('/')) {
      return '/'
    } else return '.'
  }
}
