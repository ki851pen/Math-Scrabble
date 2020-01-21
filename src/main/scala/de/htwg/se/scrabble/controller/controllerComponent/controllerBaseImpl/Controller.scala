package de.htwg.se.scrabble.controller.controllerComponent.controllerBaseImpl


import com.google.inject.Inject
import de.htwg.se.scrabble.controller.controllerComponent.GameStatus._
import de.htwg.se.scrabble.controller.controllerComponent.{ControllerInterface, _}
import de.htwg.se.scrabble.model.fileIoComponent.FileIOInterface
import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl._
import de.htwg.se.scrabble.model.gridComponent.{CardInterface, CellInterface}
import de.htwg.se.scrabble.util.{Memento, ProcessEquation, UndoManager}

import scala.swing.Publisher

class Controller @Inject()(var gameFieldCreateStrategy: GameFieldCreateStrategyTemplate, fileIO: FileIOInterface) extends ControllerInterface with Publisher{
  private var gameField: GameFieldInterface = gameFieldCreateStrategy.createNewGameField
  private var gameState: State = Init()
  private val undoManager = new UndoManager
  private var currentSum: Int = 0
  private var beginTurn: Memento = createMemento()

  def gameStatus: State = gameState
  def changeGamestatus(newState: State): Unit = gameState = newState
  def gridSize: Int = gameField.grid.size

  def cell(row: Int, col: Int): CellInterface = gameField.grid.cell(row, col)
  def getCurrentSum: Int = currentSum

  def isSet(row: Int, col: Int): Boolean = gameField.grid.cell(row, col).isSet

  def addToSum(point: Int): Unit = currentSum += point

  def getGameField: GameFieldInterface = gameField

  def setGameField(gameField: GameFieldInterface): Unit = {
    this.gameField = gameField
    publish(new GameFieldChanged)
  }

  def createMemento(): Memento = Memento(gameField, gameState, currentSum)

  def restoreFromMemento(restore: Memento): Unit = {
    this.gameField = restore.gameField
    this.gameState = restore.gameStatus
    this.currentSum = restore.currentSum
  }

  def setGrid(row: Int, col: Int, index: Int): Unit = {
    undoManager.doStep(new SetCommand(row, col, index, this))
    publish(ButtonSet(row, col))
  }

  def save: Unit = {
    fileIO.save(createMemento())
    publish(new GameFieldChanged)
    println("saved")
  }

  def load: Unit = {
    restoreFromMemento(fileIO.load)
    publish(new GameFieldChanged)
    println("loaded")
  }

  def undo: Unit = {
    undoManager.undoStep
    publish(new GameFieldChanged)
  }

  def redo: Unit = {
    undoManager.redoStep
    publish(new GameFieldChanged)
  }

  def gameToString: String = gameState.gameToString(this)

  def init: Unit = {
    println("------ Start of Initialisation ------")
    createFixedSizeGameField(15)
    fillAllHand
    beginTurn = createMemento()
  }

  def checkEquation(): Boolean = {
    ProcessEquation(this).isValid
  }

  private def takeCardsBack(): Unit = {
    restoreFromMemento(beginTurn)
    publish(new GameFieldChanged)
    publish(new InvalidEquation)
  }

  def endTurn: Unit = {
    //if submit after first card always pass checkEquation: should NOT be like this
    if (!checkEquation()) {
      takeCardsBack()
    } else {
      gameField = gameState.calPoint(this, currentSum).getOrElse(gameField)
      currentSum = 0
      fillAllHand
      undoManager.resetStack()
      publish(new GameFieldChanged)
      beginTurn = createMemento()
    }
  }

  def createFixedSizeGameField(fixedSize: Int): Unit = {
    val oldsize = gameField.grid.size
    gameFieldCreateStrategy = new GameFieldFixedSizeCreateStrategy(fixedSize)
    gameField = gameFieldCreateStrategy.createNewGameField
    gameState = FirstCard()
    if (gameField.grid.size == oldsize) publish(new GameFieldChanged) else publish(new GridSizeChanged)
  }

  def createFreeSizeGameField(sizeGrid: Int, equal: Int, plusminus: Int, muldiv: Int, digit: Int): Unit = {
    gameFieldCreateStrategy = new GameFieldFreeSizeCreateStrategy(sizeGrid, equal, plusminus, muldiv, digit)
    gameField = gameFieldCreateStrategy.createNewGameField
    gameState = FirstCard()
    publish(new GridSizeChanged)
  }

  def createPile(equal: Int, plusminus: Int, muldiv: Int, digit: Int): Unit = {
    gameField = gameField.createNewPile(equal, plusminus, muldiv, digit)
    publish(new CardsChanged)
  }

  def shufflePile: Unit = {
    gameField = gameField.shufflePile
  }

  def fillHand(name: String): Unit = {
    if (gameField.playerList.contains(name)) {
      val player = gameField.playerList(name)
      val nrLeftToFill = player.maxHandSize - player.getNrCardsInHand
      shufflePile
      gameField = gameField.fillHand(name, nrLeftToFill)
      publish(new CardsChanged)
    } else {
      println("Player " + name + " doesn't exist")
    }
  }

  def fillAllHand: Unit = {
    val playerName: Iterable[String] = gameField.playerList.keys
    playerName.foreach(p => fillHand(p))
  }

  def getCardsInHand(name: String): List[CardInterface] = {
    val player = gameField.playerList(name)
    player.hand
  }

  def clearHand(name: String): Unit = {
    if (gameField.playerList.contains(name)) {
      gameField = gameField.clearHand(name)
      shufflePile
      publish(new CardsChanged)
    } else {
      println("Player " + name + " doesn't exist")
    }
  }
  private var currentChosenCardIndex = -1

  def chooseCardInHand(indexOfCard: Int): Unit = {
    currentChosenCardIndex = indexOfCard
  }

  def currentSelectedRow: Int = currentRowOfSelectedCell
  def currentSelectedCol: Int = currentColOfSelectedCell
  private var currentRowOfSelectedCell = -1
  private var currentColOfSelectedCell = -1

  def selectedCellChanged(row: Int, col: Int): Unit = {
    currentRowOfSelectedCell = row - 1
    currentColOfSelectedCell = col - 1
    publish(new ClickChanged)
  }

  def putCardInCell: Unit = {
    setGrid(currentRowOfSelectedCell, currentColOfSelectedCell, currentChosenCardIndex)
  }

  def changeHand(playerName: String): Unit = {
    clearHand(playerName)
    fillHand(playerName)
    publish(new CardsChanged)
  }
}