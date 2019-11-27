package de.htwg.se.scrabble.model.cell

import de.htwg.se.scrabble.model.Card

private class NormalCell(value: String) extends Cell(Card(value)) {
  val cellType: String = "n"
  override def getPoint: Int = Card(value).getPoint.getOrElse(0)
  override def toString: String = if(isSet) {value} else{" "}
}