package de.htwg.se.scrabble.controller.controllerComponent

import GameStatus.State
import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gridComponent.{CardInterface, CellInterface}
import de.htwg.se.scrabble.util.Memento
import play.api.libs.json.JsObject

import scala.swing.Publisher

trait ControllerInterface extends Publisher {
  def gridSize: Int
  def getCurrentSum: Int
  def cell(row: Int, col: Int): CellInterface
  def isSet(row: Int, col: Int): Boolean
  def init: Unit
  def createFixedSizeGameField(fixedSize: Int): Unit
  def createFreeSizeGameField(sizeGrid: Int, equal: Int, plusminus: Int, muldiv: Int, digit: Int): Unit
  def getGameField: GameFieldInterface
  def createPile(equal: Int, plusminus: Int, muldiv: Int, digit: Int): Unit
  def shufflePile: Unit
  def setGrid(row: Int, col: Int, index: Int): Unit
  def endTurn: Boolean
  def please_delete_me: String
  def memToJson(mem: Memento): JsObject
  def createMemento(): Memento

  def undo: Unit
  def redo: Unit
  def save: Unit
  def load: Unit
  def fillHand(name: String): Unit
  def fillAllHand: Unit
  def getCardsInHand(name:String): List[CardInterface]
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
