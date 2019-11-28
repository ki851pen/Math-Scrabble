package de.htwg.se.scrabble.model.cell

import de.htwg.se.scrabble.model.Card

private class TripleSingleCell(value: String) extends Cell {
  val cellType: String = "t"

  override def card: Card = Card(value)
  override def getPoint: Int = card.getPoint.getOrElse(0) * 3
  override def toString: String = if(isSet) {value + "x3"} else{"x3"}
}