package de.htwg.se.scrabble.model.cell

import de.htwg.se.scrabble.model.Card

case class TripleSingleCell(value: String) extends Cell {
  val cellType: String = "t"
  override def isSet: Boolean = value != ""
  override def getPoint: Int = Card(value).getPoint * 3
  override def setCell(newValue: String): Cell = Cell(cellType,value)
  override def getvalue: String = value

  override def toString: String = if(isSet) {value + "x3"} else{"x3"}
  override def equals(other: Any): Boolean = other match {
    case t: TripleSingleCell => t.value == value
    case _ => false
  }
}