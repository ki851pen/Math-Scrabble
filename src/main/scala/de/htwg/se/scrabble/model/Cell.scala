package de.htwg.se.scrabble.model

case class Cell(value: String){
  val digit: List[Int] = (0 to 9).toList
  val operator: List[String] = List("+","-","*","/","=")
  val validlist: List[Any] = digit ::: operator
  def isSet(input : String):Boolean =
    input match{
      case "" => false
      case _ => true
    }
  //def parsevalue = if value
/*
  def matchTest = value match {
    //case 0 to 9=> "Digit"
    case "+" => "plus"
    case "-" => "minus"
    case "*" => "mul"
    case "/" => "div"
    case "=" => "equal"
    case _ => false
  }*/
}
