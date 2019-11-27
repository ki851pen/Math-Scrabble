package de.htwg.se.scrabble.model.cell

import de.htwg.se.scrabble.model.Card

case class NormalCell(value: String) extends Cell {
  val cellType: String = "n"
  override def isSet: Boolean = value != ""
  override def getPoint: Int = Card(value).getPoint
  override def setCell(newValue: String): Cell = Cell(cellType,value)
  override def getvalue: String = value

  override def toString: String = if(isSet) {value} else{" "}
  override def equals(other: Any): Boolean = other match {
    case n: NormalCell => n.value == value
    case _ => false
  }
}