import java.util.UUID

import scala.collection.mutable.ArrayBuffer

object Database {
  val Users = ArrayBuffer.empty[User]
  val Tokens = ArrayBuffer.empty[Token]
  def GetUser(UserName : String): User = {
    val index = Users.indexWhere((user) => user.username.equals(UserName))
    Users(index)
  }
  def UserWithToken(token : String): User = {
    val tokindex = Tokens.indexWhere((t) => t.key.equals(token))
    val tok = Tokens(tokindex)
    GetUser(tok.username)
  }
  def isTokenValid(token : String) : Boolean = {
    Tokens.exists(_.key.equals(token))
  }
  def Register(name : String , username : String , password : String): Either[Int,User] = {
    if(!Users.exists(_.username.equals(username))) {
      val user = User(name,username,password)
      Users.append(user)
      Right(user)
    }
    else {
      Left(RegisterFailStatus.UserAllReadyExists)
    }
  }
  def login(Username : String , Password : String): Either[Int,String] = {
    if(Users.exists(user => user.username.equals(Username))) {
      val user = GetUser(Username)
      if(user.Password.equals(Password)){
        val token = UUID.randomUUID().toString
        Database.Tokens.append(Token(token , Username))
        Right(token)
      }
      else {
        Left(LoginFailStatus.PasswordNotCorrect)
      }
    }
    else {
      Left(LoginFailStatus.UserNotExists)
    }

  }
}
case class User ( name : String , username : String , Password : String)
case class Token ( key : String , username : String)
object LoginFailStatus extends Enumeration {
  val UserNotExists = 0
  val PasswordNotCorrect = 1
}
object RegisterFailStatus extends Enumeration {
  val UserAllReadyExists = 0
}