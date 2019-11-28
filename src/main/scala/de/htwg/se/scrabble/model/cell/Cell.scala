package de.htwg.se.scrabble.model.cell

import de.htwg.se.scrabble.model.Card

trait Cell{
  val cellType: String
  def card: Card
  def getPoint: Int

  def isSet: Boolean = card.parseValue != ""
  def setCell(newValue: String): Cell = if (Card(newValue).isValid) Cell(cellType,newValue) else this
  override def equals(other: Any): Boolean = other match {
    case cell: Cell if cell.cellType == cellType => cell.card.equals(card)
    case _ => false
  }
}

object Cell {
  def apply(kind: String, value: String): Cell = kind match {
    case "n" => new NormalCell(value)
    case "d" => new DoubleSingleCell(value)
    case "t" => new TripleSingleCell(value)
  }
  def apply(value: String): Cell = new NormalCell(value)
}
