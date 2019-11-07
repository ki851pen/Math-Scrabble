package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.util.SpecialCell._
case class Cell(obj: Either[Card,String], special: SpecialCell){

  def this(value : String) = this(card, Normal)
  val operator: List[String] = List("+","-","*","/","=")
  val validlist: List[String] = (0 to 9).toList.map(x => x.toString) ::: operator
  def isSet : Boolean = card != ""
  def isNormal : Boolean = special == Normal

  override def toString: String = {
    if (card.value == "") " "
    card.value
  }
}
