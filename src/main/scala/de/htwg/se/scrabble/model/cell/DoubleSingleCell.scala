package de.htwg.se.scrabble.model.cell

import de.htwg.se.scrabble.model.Card
import de.htwg.se.scrabble.model.cell.Cell

private class DoubleSingleCell(value: String) extends Cell(Card(value)) {
  val cellType: String = "d"
  override def getPoint: Int = Card(value).getPoint.getOrElse(0) * 2
  override def toString: String = if(isSet) {value + "x2"} else{"x2"}
}
