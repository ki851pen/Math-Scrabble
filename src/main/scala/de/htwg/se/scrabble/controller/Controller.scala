package de.htwg.se.scrabble.controller

import GameStatus._
import de.htwg.se.scrabble.model.gameField._
import de.htwg.se.scrabble.model.{Card, Pile, Player}
import de.htwg.se.scrabble.util.Observable

class Controller(private var gameFieldCreateStrategy: GameFieldCreateStrategyTemplate) extends Observable{
  private var gameField: GameField = gameFieldCreateStrategy.createNewGameField()
  var gameStatus: GameStatus = INIT
  private var currentSum: Int = 0

  def init(): Unit = {
    println("------ Start of Initialisation ------")
    gameStatus = P1
  }

  def quit(): Unit = {
    gameStatus = END_GAME
  }

  def endturn(): Unit = {
    gameStatus = gameStatus match {
      case P1 => P2
      case P2 => P1
    }
  }

  def createFixedSizeGameField(fixedSize: Int): Unit ={
    gameFieldCreateStrategy = new GameFieldFixedSizeCreateStrategy(fixedSize)
    gameField = gameFieldCreateStrategy.createNewGameField()
    notifyObservers
  }

  def createFreeSizeGameField(sizeGrid: Int, equal:Int, plusminus:Int, muldiv:Int, blank:Int, digit:Int): Unit ={
    gameFieldCreateStrategy = new GameFieldFreeSizeCreateStrategy(sizeGrid, equal, plusminus, muldiv, blank, digit)
    gameField = gameFieldCreateStrategy.createNewGameField()
    notifyObservers
  }

  def setGrid(player: String, row: String, col: String, value: String): Unit ={
    if(gameField.playerList(player).hand.contains(Card(value))) {
      if(!gameField.grid.isEmpty || (row == col && gameField.grid.size / 2 + 1 == row.toInt)) {
        gameField = gameField.copy(grid = gameField.grid.set(row.toInt-1, col.toInt-1, value), playerList = gameField.changePlayerAttr(player,gameField.playerList(player).useCard(Card(value))))
        currentSum += gameField.grid.cell(row.toInt-1, col.toInt-1).getPoint
        println(currentSum)
        notifyObservers
      } else {
        println("First Cell to set have to be in Middle of the Grid")
      }
    } else {
      println("Can only set card from hand")
    }
  }
  def createPile(equal:Int, plusminus:Int, muldiv:Int, blank:Int, digit:Int): Unit = {
    gameField = gameField.copy(pile = new Pile(equal, plusminus, muldiv, blank, digit))
    notifyObservers
  }
  def shufflePile():Unit = {
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
    notifyObservers
  }

  def addPlayer(name: String): Unit ={
    gameField = gameField.copy(playerList = gameField.createPlayer(name))
    notifyObservers
  }

  def removePlayer(name: String): Unit ={
    gameField = gameField.copy(playerList = gameField.deletePlayer(name))
    notifyObservers
  }

  def getGameField: GameField = gameField

  def gameToString: String = gameField.toString
}
