package de.htwg.se.scrabble.controller.controllerComponent.controllerMockImpl

import de.htwg.se.scrabble.controller.GameStatus
import de.htwg.se.scrabble.controller.GameStatus.Init
import de.htwg.se.scrabble.controller.controllerComponent.ControllerInterface
import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl.{GameFieldCreateStrategyTemplate, GameFieldFreeSizeCreateStrategy}
import de.htwg.se.scrabble.model.gameFieldComponent.gridComponent.cellComponent.CellInterface

case class Controller(private var gameFieldCreateStrategy: GameFieldCreateStrategyTemplate) extends ControllerInterface {
  gameFieldCreateStrategy = new GameFieldFreeSizeCreateStrategy(1, 1, 1, 1, 1, 1)
  private var gameField = gameFieldCreateStrategy.createNewGameField
  override def gridSize: Int = 1

  override def cell(row: Int, col: Int): CellInterface = gameField.grid.cell(row, col)

  override def isSet(row: Int, col: Int): Boolean = false

  override def init: Unit = {}

  override def createFixedSizeGameField(fixedSize: Int): Unit = {}

  override def createFreeSizeGameField(sizeGrid: Int, equal: Int, plusminus: Int, muldiv: Int, blank: Int, digit: Int): Unit = {}

  override def getGameField: GameFieldInterface = gameField

  override def createPile(equal: Int, plusminus: Int, muldiv: Int, blank: Int, digit: Int): Unit = {}

  override def shufflePile: Unit = {}

  override def setGrid(row: Int, col: Int, index: Int): Unit = {}

  override def endTurn: Unit = {}

  override def undo: Unit = {}

  override def redo: Unit = {}

  override def fillHand(name: String): Unit = {}

  override def fillAllHand: Unit = {}

  override def clearHand(name: String): Unit = {}

  override def gameStatus: GameStatus.State = Init()

  override def currentSelectedRow: Int = 0

  override def currentSelectedCol: Int = 0

  override def selectedCellChanged(row: Int, col: Int): Unit = {}

  override def chooseCardInHand(indexOfCard: Int): Unit = {}

  override def changeHand(playerName: String): Unit = {}

  override def putCardInCell: Unit = {}

  override def gameToString: String = ""

  override def changeGamestatus(newState: GameStatus.State): Unit = ???
}
