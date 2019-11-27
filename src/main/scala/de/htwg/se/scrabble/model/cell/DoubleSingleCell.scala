package de.htwg.se.scrabble.model.cell

import de.htwg.se.scrabble.model.Card

case class DoubleSingleCell(value: String) extends Cell {
  val cellType: String = "d"
  override def isSet: Boolean = value != ""
  override def getPoint: Int = Card(value).getPoint * 2
  override def setCell(newValue: String): Cell = Cell(cellType,value)
  override def getvalue: String = value

  override def toString: String = if(isSet) {value + "x2"} else{"x2"}
  override def equals(other: Any): Boolean = other match {
    case d: DoubleSingleCell => d.value == value
    case _ => false
  }
}
