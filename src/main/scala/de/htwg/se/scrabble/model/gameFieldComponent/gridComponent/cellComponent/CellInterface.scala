package de.htwg.se.scrabble.model.gameFieldComponent.gridComponent.cellComponent

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
