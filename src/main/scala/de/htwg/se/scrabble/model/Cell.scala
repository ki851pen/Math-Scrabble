package de.htwg.se.scrabble.model

case class Cell(value: String){
  val operator: List[String] = List("+","-","*","/","=")
  val validlist: List[String] = (0 to 9).toList.map(x => x.toString) ::: operator
  def isSet:Boolean =
    value match{
      case "" => false
      case _ => true
    }
  def isvalid: Boolean = validlist.contains(value)
  def parsevalue: Any = if(operator.contains(value)){value} else {value.toIntOption.getOrElse("")}

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
