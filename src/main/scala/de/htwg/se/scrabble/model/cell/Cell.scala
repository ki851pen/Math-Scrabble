package de.htwg.se.scrabble.model.cell

import de.htwg.se.scrabble.model.Card

trait Cell {
  val cellType: String
  def isSet: Boolean
  def getPoint: Int
  def setCell(newValue: String): Cell
  def getvalue: String
}

object Cell {
  def apply(kind: String, value: String): Cell = kind match {
    case "n" => NormalCell(value)
    case "d" => DoubleSingleCell(value)
    case "t" => TripleSingleCell(value)
  }
  def apply(value: String): Cell = NormalCell(value)
}
