package de.htwg.se.scrabble.model.cell

import de.htwg.se.scrabble.model.Card

trait Cell {
  val cellType: String
  val card: Either[Card, String]
  def getPoint: Int
  def isSet: Boolean = card != ""
  def setCell(newValue: String): Cell = if (Card(newValue).isValid) Cell(cellType,newValue) else this
}

object Cell {
  def apply(kind: String, value: String): Cell = kind match {
    case "n" => NormalCell(value)
    case "d" => DoubleSingleCell(value)
    case "t" => TripleSingleCell(value)
  }
  def apply(value: String): Cell = NormalCell(value)
}
