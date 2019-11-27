package de.htwg.se.scrabble.model.cell

import de.htwg.se.scrabble.model.Card
import de.htwg.se.scrabble.model.cell.Cell

case class DoubleSingleCell(value: String) extends Cell {
  val cellType: String = "d"
  val card: Either[Card, String] = if (Card(value).isValid) Left(Card(value)) else Right("")
  override def getPoint: Int = Card(value).getPoint.getOrElse(0) * 2

  override def toString: String = if(isSet) {card.left.toString + "x2"} else{"x2"}
  override def equals(other: Any): Boolean = other match {
    case d: DoubleSingleCell => d.value == value
    case _ => false
  }
}
