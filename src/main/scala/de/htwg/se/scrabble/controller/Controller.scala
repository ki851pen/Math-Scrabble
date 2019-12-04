package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.controller.GameStatus._
import de.htwg.se.scrabble.model.gameField._
import de.htwg.se.scrabble.model.{Card, Pile, Player, Grid}
import de.htwg.se.scrabble.util.Observable
import de.htwg.se.scrabble.model.cell.Cell

class Controller(private var gameFieldCreateStrategy: GameFieldCreateStrategyTemplate) extends Observable {
  private var gameField: GameField = gameFieldCreateStrategy.createNewGameField()
  private var currentSum: Int = 0
  var gameStatus: State = Init()

  def getGameField: GameField = gameField
  def gridSize(): Int = gameField.grid.size
  def cell(row:Int, col:Int): Cell = gameField.grid.cell(row, col)
  def isSet(row:Int, col:Int): Boolean = gameField.grid.cell(row, col).isSet

  def init(): Unit = gameStatus.init(this)

  def endTurn(): Unit = {
    //todo check if equation is valid
    //todo if (double equation -> point *2)
    gameField = gameStatus.calPoint(this, currentSum).getOrElse(gameField)
    currentSum = 0
    fillAllHand()
    notifyObservers
  }

  def createFixedSizeGameField(fixedSize: Int): Unit = {
    gameFieldCreateStrategy = new GameFieldFixedSizeCreateStrategy(fixedSize)
    gameField = gameFieldCreateStrategy.createNewGameField()
    gameStatus = firstCard()
    notifyObservers
  }

  def createFreeSizeGameField(sizeGrid: Int, equal: Int, plusminus: Int, muldiv: Int, blank: Int, digit: Int): Unit = {
    gameFieldCreateStrategy = new GameFieldFreeSizeCreateStrategy(sizeGrid, equal, plusminus, muldiv, blank, digit)
    gameField = gameFieldCreateStrategy.createNewGameField()
    gameStatus = firstCard()
    notifyObservers
  }

  def setGrid(row: String, col: String, value: String): Unit = {
    val either = gameStatus.setGrid(this, row, col, value)
    either match {
      case Left(x) => gameField = x; currentSum += gameField.grid.cell(row.toInt-1, col.toInt-1).getPoint; notifyObservers;
      case Right(x) => println(x)
    }
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

  def addPlayer(name: String): Unit = {
    gameField = gameField.copy(playerList = gameField.createPlayer(name))
    notifyObservers
  }

  def removePlayer(name: String): Unit = {
    gameField = gameField.copy(playerList = gameField.deletePlayer(name))
    notifyObservers
  }

  def gameToString: String = gameStatus.gameToString(this)
}
