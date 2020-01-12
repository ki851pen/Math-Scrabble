package de.htwg.se.scrabble.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.scrabble.controller.GameStatus._
import de.htwg.se.scrabble.controller._
import de.htwg.se.scrabble.controller.controllerComponent.ControllerInterface
import de.htwg.se.scrabble.model.gameFieldComponent.gridComponent.cellComponent.CardInterface
import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl.{GameFieldCreateStrategyTemplate, GameFieldFixedSizeCreateStrategy, GameFieldFreeSizeCreateStrategy}
import de.htwg.se.scrabble.util.{Memento, UndoManager}

import scala.util.control.Breaks._

class Controller(private var gameFieldCreateStrategy: GameFieldCreateStrategyTemplate) extends ControllerInterface {
  private var gameField: GameFieldInterface = gameFieldCreateStrategy.createNewGameField
  private var gameState: State = Init()
  private val undoManager = new UndoManager
  private var currentSum: Int = 0

  def gameStatus = gameState
  def changeGamestatus(newState: State) = gameState = newState
  def gridSize: Int = gameField.grid.size

  def getCurrentSum: Int = currentSum

  def cell(row: Int, col: Int) = gameField.grid.cell(row, col)

  def isSet(row: Int, col: Int): Boolean = gameField.grid.cell(row, col).isSet

  def addToSum(point: Int): Unit = currentSum += point

  def getGameField: GameFieldInterface = gameField

  def setGameField(gameField: GameFieldInterface): Unit = {
    this.gameField = gameField
    publish(new GameFieldChanged)
  }

  def setstate(gameField: GameFieldInterface, gameStatus: State, currentSum: Int): Unit = {
    this.gameField = gameField
    this.gameState = gameStatus
    this.currentSum = currentSum
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
    //publish(new GameFieldChanged)
  }

  def undo(): Unit = {
    undoManager.undoStep
    publish(new GameFieldChanged)
  }

  def redo(): Unit = {
    undoManager.redoStep
    publish(new GameFieldChanged)
  }

  def gameToString: String = gameState.gameToString(this)

  def init(): Unit = {
    println("------ Start of Initialisation ------")
    createFixedSizeGameField(15)
    fillAllHand()
  }

  def checkEquation(): Boolean = {
    //funktioniert noch nicht
    val newCells = gameState.asInstanceOf[P].getNewCells
    for (cell <- newCells) {
      breakable {
        if (this.cell(cell._1, cell._2).card.isQuestionMark) break
        val neighbors = gameField.grid.getNeighborsOf(cell._1, cell._2)
        for (neighbor <- neighbors) {
          if (this.cell(cell._1, cell._2).card.isOperator && neighbor._2.card.isOperator) return false
        }
      }
    }
    true
  }

  private def takeCardsBack(): Unit = {
    val cardsToTakeBack = gameState.asInstanceOf[P].getNewCells
    val currentPlayer = "A"
    //gameField = gameField.copy(grid = gameField.grid.clearCells(cardsToTakeBack),
      //playerList = gameField.changePlayerAttr(currentPlayer, gameField.playerList(currentPlayer).addToHand(cardsToTakeBack.map(pos => cell(pos._1, pos._2).card))))
  //not done
  }

  def endTurn(): Unit = {
    //ProcessEquation(this)
    //if (!checkEquation()) takeCardsBack()
    gameField = gameState.calPoint(this, currentSum).getOrElse(gameField)
    currentSum = 0
    fillAllHand()
    undoManager.resetStack()
    publish(new GameFieldChanged)
  }

  def createFixedSizeGameField(fixedSize: Int): Unit = {
    val oldsize = gameField.grid.size
    gameFieldCreateStrategy = new GameFieldFixedSizeCreateStrategy(fixedSize)
    gameField = gameFieldCreateStrategy.createNewGameField
    gameState = FirstCard()
    if (gameField.grid.size == oldsize) publish(new GameFieldChanged) else publish(new GridSizeChanged)
  }

  def createFreeSizeGameField(sizeGrid: Int, equal: Int, plusminus: Int, muldiv: Int, blank: Int, digit: Int): Unit = {
    gameFieldCreateStrategy = new GameFieldFreeSizeCreateStrategy(sizeGrid, equal, plusminus, muldiv, blank, digit)
    gameField = gameFieldCreateStrategy.createNewGameField
    gameState = FirstCard()
    publish(new GridSizeChanged)
  }

  def createPile(equal: Int, plusminus: Int, muldiv: Int, blank: Int, digit: Int): Unit = {
    gameField = gameField.createNewPile(equal, plusminus, muldiv, blank, digit)
    publish(new CardsChanged)
  }

  def shufflePile(): Unit = {
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

  def fillAllHand(): Unit = {
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
  private var currentChosenCardIndex = -1;
  def chooseCardInHand(indexOfCard: Int): Unit = {
    currentChosenCardIndex = indexOfCard
  }
  def currentSelectedRow = currentRowOfSelectedCell
  def currentSelectedCol = currentColOfSelectedCell
  private var currentRowOfSelectedCell = -1
  private var currentColOfSelectedCell = -1

  def selectedCellChanged(row: Int, col: Int): Unit = {
    currentRowOfSelectedCell = row - 1
    currentColOfSelectedCell = col - 1
    publish(new ClickChanged)
  }

  def putCardInCell = {
    setGrid(currentRowOfSelectedCell, currentColOfSelectedCell, currentChosenCardIndex)
  }

  def changeHand(playerName: String): Unit = {
    clearHand(playerName)
    fillHand(playerName)
    publish(new CardsChanged)
  }
}