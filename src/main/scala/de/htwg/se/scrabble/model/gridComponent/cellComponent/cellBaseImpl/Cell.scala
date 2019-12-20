package de.htwg.se.scrabble.model.gridComponent.cellComponent.cellBaseImpl

import de.htwg.se.scrabble.model.gridComponent.cellComponent.CellInterface

abstract class Cell(value: String) extends CellInterface{

  val cellType: String

  def card: Card = Card(value)

  def isSet: Boolean = card.isValid

  def isNormal = false

  def isDouble = false

  def isTriple = false

  def setCell(newValue: String): Cell = if(Card(newValue).isValid) Cell(cellType, newValue) else this

  override def equals(other: Any): Boolean = other match {
    case cell: Cell if cell.cellType == cellType => cell.card.equals(card)
    case _ => false
  }
}

object Cell {
  def apply(kind: String, value: String): Cell = kind match {
    case "n" => NormalCell(value)
    case "d" => DoubleSingleCell(value)
    case "t" => TripleSingleCell(value)
  }

  def apply(value: String): Cell = NormalCell(value)
}
