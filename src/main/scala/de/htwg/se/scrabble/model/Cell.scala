package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.util.SpecialCell._
case class Cell(value: String, special: SpecialCell){
  def this(value : String) = this(value, Normal)
  val operator: List[String] = List("+","-","*","/","=")
  val validlist: List[String] = (0 to 9).toList.map(x => x.toString) ::: operator
  def isSet : Boolean = value != ""
  def isNormal : Boolean = special == Normal
  def isValid: Boolean = validlist.contains(value)
  def parseValue: Any = if(operator.contains(value)){value} else {value.toIntOption.getOrElse("")}
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
  override def toString: String = value
}
