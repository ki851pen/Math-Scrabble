package de.htwg.se.scrabble.model.cell

import de.htwg.se.scrabble.model.Card

case class TripleSingleCell(value: String) extends Cell {
  val cellType: String = "t"
  val card: Either[Card, String] = if (Card(value).isValid) Left(Card(value)) else Right("")
  override def getPoint: Int = Card(value).getPoint.getOrElse(0) * 3

  override def toString: String = if(isSet) {value + "x3"} else{"x3"}
  override def equals(other: Any): Boolean = other match {
    case t: TripleSingleCell => t.value == value
    case _ => false
  }
}