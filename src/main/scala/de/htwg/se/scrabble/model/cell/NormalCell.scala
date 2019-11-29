package de.htwg.se.scrabble.model.cell

import de.htwg.se.scrabble.model.Card

private class NormalCell(value: String) extends Cell {
  val cellType: String = "n"

  override def card: Card = Card(value)
  override def getPoint: Int = card.getPoint.getOrElse(0)
  override def toString: String = if(isSet) {value} else{" "}
}