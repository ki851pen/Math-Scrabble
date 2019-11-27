package de.htwg.se.scrabble.model.cell

import de.htwg.se.scrabble.model.Card

case class NormalCell(value: String) extends Cell {
  val cellType: String = "n"
  val card: Either[Card, String] = if (Card(value).isValid) Left(Card(value)) else Right("")
  override def getPoint: Int = Card(value).getPoint.getOrElse(0)

  override def toString: String = if(isSet) {value} else{" "}
  override def equals(other: Any): Boolean = other match {
    case n: NormalCell => n.value == value
    case _ => false
  }
}