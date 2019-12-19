package de.htwg.se.scrabble.model.cellComponent

import de.htwg.se.scrabble.model.Card
import de.htwg.se.scrabble.model.cellComponent.Cell

private class DoubleSingleCell(value: String) extends Cell{
  val cellType: String = "d"

  override def card: Card = Card(value)
  override def getPoint: Int = card.getPoint.getOrElse(0) * 2
  override def toString: String = if(isSet) {value} else{"x2"}
}
