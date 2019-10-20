package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.util.SpecialCell._
case class Cell(value: String, special: SpecialCell){
  def this(value : String) = this(value, Normal)
  def isSet : Boolean = value != ""
  def isNormal : Boolean = special == Normal
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

