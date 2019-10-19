package de.htwg.se.scrabble.model

case class Cell(value: String){
  val validlist: List[String] = (0 to 9).toList.map(x => x.toString) ::: List("+","-","*","/","=")
  def isSet(input : String):Boolean =
    input match{
      case "" => false
      case _ => true
    }
  def isvalid: Boolean = validlist.contains(value)
  def parsevalue: Int = ??? //value.filter(x => !operator.contains(x)).toIntOption.getOrElse(-1)
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
