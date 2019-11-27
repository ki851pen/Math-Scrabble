package de.htwg.se.scrabble.model.cell

import de.htwg.se.scrabble.model.Card

private class TripleSingleCell(value: String) extends Cell(Card(value)) {
  val cellType: String = "t"
  override def getPoint: Int = Card(value).getPoint.getOrElse(0) * 3
  override def toString: String = if(isSet) {value + "x3"} else{"x3"}
}