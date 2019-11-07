package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.util.SpecialCell._
case class Cell(value: String, special: SpecialCell){
  def this(value: String) = this(value, Normal)
  def isNormal : Boolean = special == Normal
  def isSet : Boolean = value != ""

  override def toString: String = {
    if(isSet) value
    " "
  }
}
