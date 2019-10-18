package de.htwg.se.scrabble.model

case class Cell(value: String){
  def isSet(input : String):Boolean =
    input match{
      case "" => false
      case _ => true
  }
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
