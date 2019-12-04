package de.htwg.se.scrabble.controller

import GameStatus._
import de.htwg.se.scrabble.model.gameField._
import de.htwg.se.scrabble.model.{Card, Pile, Player, Grid}
import de.htwg.se.scrabble.util.Observable
import de.htwg.se.scrabble.model.cell.Cell

class Controller(private var gameFieldCreateStrategy: GameFieldCreateStrategyTemplate) extends Observable {
  private var gameField: GameField = gameFieldCreateStrategy.createNewGameField()
  var gameStatus: GameStatus = INIT
  private var currentSum: Int = 0

  def gridSize(): Int = gameField.grid.size

  def cell(row:Int, col:Int): Cell = gameField.grid.cell(row, col)

  def isSet(row:Int, col:Int): Boolean = gameField.grid.cell(row, col).isSet

  def init(): Unit = {
    println("------ Start of Initialisation ------")
    createFixedSizeGameField(15)
    fillAllHand()
  }

  def quit(): Unit = {
    gameStatus = END_GAME
    notifyObservers
    println("Bye")
    System.exit(0)
  }

  def calPoint(): Unit = {
    //todo check if equation is valid
    //todo if (double equation -> point *2)
    gameStatus match {
      case P1 => gameField = gameField.copy(playerList = gameField.changePlayerAttr("A", gameField.playerList("A").copy(point = gameField.playerList("A").point + currentSum)))
        gameStatus = P2;
        fillHand("A")
      case P2 => gameField = gameField.copy(playerList = gameField.changePlayerAttr("B", gameField.playerList("B").copy(point = gameField.playerList("B").point + currentSum)))
        gameStatus = P1;
        fillHand("B")
    }
    currentSum = 0
    notifyObservers
  }

  def createFixedSizeGameField(fixedSize: Int): Unit = {
    gameFieldCreateStrategy = new GameFieldFixedSizeCreateStrategy(fixedSize)
    gameField = gameFieldCreateStrategy.createNewGameField()
    gameStatus = P1
    notifyObservers
  }

  def createFreeSizeGameField(sizeGrid: Int, equal: Int, plusminus: Int, muldiv: Int, blank: Int, digit: Int): Unit = {
    gameFieldCreateStrategy = new GameFieldFreeSizeCreateStrategy(sizeGrid, equal, plusminus, muldiv, blank, digit)
    gameField = gameFieldCreateStrategy.createNewGameField()
    gameStatus = P1
    notifyObservers
  }

  def setGrid(player: String, row: String, col: String, value: String): Unit = {
    gameStatus match {
      //case FIRST_CARD if !(row == col && gameField.grid.size / 2 + 1 == row.toInt) => println("First Cell to set have to be in Middle of the Grid")
      //case FIRST_CARD if player == "B" => gameStatus = P2
      case P1 if player != "A" => println("It's A's turn")
      case P2 if player != "B" => println("It's B's turn")
      case P1 | P2 if !gameField.playerList(player).hand.contains(Card(value)) => println("Can only set card from hand")
      case P1 | P2 if gameField.grid.cell(row.toInt - 1, col.toInt - 1).isSet => println("can't set already set cell")
      case P1 | P2 =>
        gameField = gameField.copy(grid = gameField.grid.set(row.toInt - 1, col.toInt - 1, value), playerList = gameField.changePlayerAttr(player, gameField.playerList(player).useCard(Card(value))))
        currentSum += gameField.grid.cell(row.toInt - 1, col.toInt - 1).getPoint
        println(currentSum)
        notifyObservers
      case _ => println("cannot set grid if not in player turn")
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

  def getGameField: GameField = gameField

  def gameToString: String = gameStatus match {
    case P1 => gameField.gameToString("A")
    case P2 => gameField.gameToString("B")
    case _ => gameField.gameToString("A")
  }
}
