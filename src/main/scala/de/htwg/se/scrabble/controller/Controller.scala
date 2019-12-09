package de.htwg.se.scrabble.controller

import util.control.Breaks._
import de.htwg.se.scrabble.controller.GameStatus._
import de.htwg.se.scrabble.model.Pile
import de.htwg.se.scrabble.model.cell.Cell
import de.htwg.se.scrabble.model.gameField._
import de.htwg.se.scrabble.util.{Memento, Observable, UndoManager}

class Controller(private var gameFieldCreateStrategy: GameFieldCreateStrategyTemplate) extends Observable {
  private var gameField: GameField = gameFieldCreateStrategy.createNewGameField
  var gameStatus: State = Init()
  private val undoManager = new UndoManager
  private var currentSum: Int = 0

  def gridSize: Int = gameField.grid.size

  def cell(row: Int, col: Int): Cell = gameField.grid.cell(row, col)

  def isSet(row: Int, col: Int): Boolean = gameField.grid.cell(row, col).isSet

  def addToSum(point: Int): Unit = currentSum += point

  def getGameField: GameField = gameField

  def setGameField(gameField: GameField): Unit = {
    this.gameField = gameField
    notifyObservers
  }

  def setstate(gameField: GameField, gameStatus: State, currentSum: Int): Unit = {
    this.gameField = gameField
    this.gameStatus = gameStatus
    this.currentSum = currentSum
  }

  def createMemento(): Memento = Memento(gameField, gameStatus, currentSum)

  def restoreFromMemento(restore: Memento): Unit = {
    this.gameField = restore.gameField
    this.gameStatus = restore.gameStatus
    this.currentSum = restore.currentSum
  }

  //when set a grid when have ? can use everything set ?2
  //cant set a grid when corner cell and cell nearby are already set
  def setGrid(row: Int, col: Int, value: String): Unit = {
    undoManager.doStep(new SetCommand(row, col, value: String, this))
  }

  def undo(): Unit = {
    undoManager.undoStep
    notifyObservers
  }

  def redo(): Unit = {
    undoManager.redoStep
    notifyObservers
  }

  def gameToString: String = gameStatus.gameToString(this)

  def init(): Unit = {
    println("------ Start of Initialisation ------")
    createFixedSizeGameField(15)
    fillAllHand()
  }

  def checkEquation(): Boolean = {
    //funktioniert noch nicht
    val newCells = gameStatus.asInstanceOf[P].getNewCells
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
    val cardsToTakeBack = gameStatus.asInstanceOf[P].getNewCells
    val currentPlayer = "A"
    gameField = gameField.copy(grid = gameField.grid.clearCells(cardsToTakeBack),
      playerList = gameField.changePlayerAttr(currentPlayer, gameField.playerList(currentPlayer).addToHand(cardsToTakeBack.map(pos => cell(pos._1, pos._2).card))))
  //not done
  }

  def endTurn(): Unit = {
    //todo check if equation is valid
    //todo if (double equation -> point *2)
    if (!checkEquation()) takeCardsBack()
    gameField = gameStatus.calPoint(this, currentSum).getOrElse(gameField)
    currentSum = 0
    fillAllHand()
    undoManager.resetStack()
    notifyObservers
  }

  def createFixedSizeGameField(fixedSize: Int): Unit = {
    gameFieldCreateStrategy = new GameFieldFixedSizeCreateStrategy(fixedSize)
    gameField = gameFieldCreateStrategy.createNewGameField
    gameStatus = FirstCard()
    notifyObservers
  }

  def createFreeSizeGameField(sizeGrid: Int, equal: Int, plusminus: Int, muldiv: Int, blank: Int, digit: Int): Unit = {
    gameFieldCreateStrategy = new GameFieldFreeSizeCreateStrategy(sizeGrid, equal, plusminus, muldiv, blank, digit)
    gameField = gameFieldCreateStrategy.createNewGameField
    gameStatus = FirstCard()
    notifyObservers
  }

  def createPile(equal: Int, plusminus: Int, muldiv: Int, blank: Int, digit: Int): Unit = {
    gameField = gameField.copy(pile = new Pile(equal, plusminus, muldiv, blank, digit))
    notifyObservers
  }

  def shufflePile(): Unit = {
    gameField = gameField.copy(pile = gameField.pile.shuffle)
    notifyObservers
  }

  def fillHand(name: String): Unit = {
    if (gameField.playerList.contains(name)) {
      val player = gameField.playerList(name)
      val nrLeftToFill = player.maxHandSize - player.getNrCardsInHand
      shufflePile()
      gameField = GameField(gameField.grid, gameField.pile.drop(nrLeftToFill), gameField.changePlayerAttr(name, gameField.playerList(name).addToHand(gameField.pile.take(nrLeftToFill))))
      notifyObservers
    } else {
      println("Player " + name + " doesn't exist")
    }
  }

  def fillAllHand(): Unit = {
    val playerName: Iterable[String] = gameField.playerList.keys
    playerName.foreach(p => fillHand(p))
  }

  def clearHand(name: String): Unit = {
    if (gameField.playerList.contains(name)) {
      val player = gameField.playerList(name)
      gameField = gameField.copy(pile = Pile(gameField.pile.tilepile ::: player.hand), playerList = gameField.changePlayerAttr(player.name, gameField.playerList(player.name).copy(hand = Nil)))
      shufflePile()
      notifyObservers
    } else {
      println("Player " + name + " doesn't exist")
    }
  }

  /// Nur zum Testen da
  def CgetRow(row: Int): Unit = {
    println(gameField.grid.getRow(row).mkString(", "))
  }

  def CgetCol(col: Int): Unit = {
    println(gameField.grid.getCol(col).mkString(", "))
  }

  /*def getNeighbors(row: String,col: String) = {
    println(gameField.grid.getNeighbors(row.toInt -1, col.toInt -1))
  }*/
}