package de.htwg.se.scrabble.controller.controllerComponent

import de.htwg.se.scrabble.controller.GameStatus.State
import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gameFieldComponent.gridComponent.cellComponent.CellInterface

import scala.swing.Publisher

trait ControllerInterface extends Publisher {
  def gridSize: Int
  def cell(row: Int, col: Int): CellInterface
  def isSet(row: Int, col: Int): Boolean
  def init: Unit
  def createFixedSizeGameField(fixedSize: Int): Unit
  def createFreeSizeGameField(sizeGrid: Int, equal: Int, plusminus: Int, muldiv: Int, blank: Int, digit: Int): Unit
  def getGameField: GameFieldInterface
  def createPile(equal: Int, plusminus: Int, muldiv: Int, blank: Int, digit: Int): Unit
  def shufflePile: Unit
  def setGrid(row: Int, col: Int, index: Int): Unit
  def endTurn: Unit

  def undo: Unit
  def redo: Unit
  def fillHand(name: String): Unit
  def fillAllHand: Unit
  def clearHand(name: String): Unit
  def gameStatus: State
  def changeGamestatus(newState: State): Unit
  def currentSelectedRow: Int
  def currentSelectedCol: Int
  def selectedCellChanged(row: Int, col: Int)
  def chooseCardInHand(indexOfCard: Int)
  def changeHand(playerName: String)
  def putCardInCell: Unit
  def gameToString: String
}
