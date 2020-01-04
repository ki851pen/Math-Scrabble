package de.htwg.se.scrabble.model.gridComponent

trait GridInterface {
  def cell(row: Int, col: Int): CellInterface

  def isEmpty: Boolean

  def set(row: Int, col: Int, value: String): GridInterface

  def size: Int

  def getNeighborsOf(row: Int, col: Int): Map[(Int, Int), CellInterface]
}

trait CellInterface {
  def card: CardInterface

  def getPoint: Int

  def isSet: Boolean

  def isNormal: Boolean

  def isDouble: Boolean

  def isTriple: Boolean

  def setCell(newValue: String): CellInterface
}

trait CardInterface {
  def isValid: Boolean

  def isDigit: Boolean

  def isQuestionMark: Boolean

  def isOperator: Boolean

  def getPoint: Option[Int]
}
