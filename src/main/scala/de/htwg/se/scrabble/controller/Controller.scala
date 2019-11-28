package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.model.{Card, GameField, GameFieldCreateStrategyTemplate, GameFieldFixedSizeCreateStrategy, GameFieldFreeSizeCreateStrategy, Grid, Pile}
import de.htwg.se.scrabble.util.Observable

class Controller(private var gameFieldCreateStrategy: GameFieldCreateStrategyTemplate) extends Observable{
  private var gameField: GameField = gameFieldCreateStrategy.createNewGameField()
  def createEmptyGrid(size: Int):Unit = {
    createFixedSizeGameField(size)
  }

  def createFixedSizeGameField(fixedSize: Int) ={
    gameFieldCreateStrategy = new GameFieldFixedSizeCreateStrategy(fixedSize)
    gameField = gameFieldCreateStrategy.createNewGameField()
    notifyObservers
  }

  def createFreeSizeGameField(sizeGrid: Int, equal:Int, plusminus:Int, muldiv:Int, blank:Int, digit:Int) ={
    gameFieldCreateStrategy = new GameFieldFreeSizeCreateStrategy(sizeGrid, equal, plusminus, muldiv, blank, digit)
    gameField = gameFieldCreateStrategy.createNewGameField()
    notifyObservers
  }

  def setGrid(player: String, row: String, col: String, value: String): Unit ={
    if(gameField.playerList(player).hand.contains(Card(value))) {
      if(!gameField.grid.isEmpty || (row == col && gameField.grid.size / 2 + 1 == row.toInt)) {
        gameField = gameField.copy(grid = gameField.grid.set(row.toInt-1, col.toInt-1, value), playerList = gameField.replacePlayer(player,gameField.playerList(player).useCard(Card(value))))
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
  def takeFromPile(name: String, size:Int): Unit = {
    if (gameField.playerList.contains(name)) {
      shufflePile()
      gameField = GameField(gameField.grid, gameField.pile.drop(size), gameField.replacePlayer(name, gameField.playerList(name).addToHand(gameField.pile.take(size))))
      notifyObservers
      /*if (game.playerList(name).getNrCardsInHand + size < game.playerList(name).maxHandSize) {
        shufflePile()
        game = Gamefield(game.grid, game.pile.drop(size), game.replacePlayer(name, game.playerList(name).addToHand(game.pile.take(size))))
        notifyObservers
      }
      else {println("Maximun Cards in Hand is 9")}*/
    } else {
      println("Player " + name + " doesn't exist")
    }
  }

  def addPlayer(name: String): Unit ={
    gameField = gameField.copy(playerList = gameField.createPlayer(name))
    notifyObservers
  }

  def removePlayer(name: String): Unit ={
    gameField = gameField.copy(playerList = gameField.deletePlayer(name))
    notifyObservers
  }

  def fillAllHand(): Unit = {
    val nrLeftToFill: Iterable[Int] = gameField.playerList.values.map(player => player.maxHandSize - player.getNrCardsInHand)
    val playername: Iterable[String] = gameField.playerList.keys
    (playername,nrLeftToFill).zipped.foreach((p,n) => takeFromPile(p,n))
    notifyObservers
  }

  def getGameField = gameField

  def gameToString: String = gameField.toString
}
