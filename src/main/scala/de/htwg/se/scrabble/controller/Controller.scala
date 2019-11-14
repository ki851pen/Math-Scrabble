package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.model.{Gamefield, Grid, Pile}
import de.htwg.se.scrabble.util.Observable

class Controller(var game: Gamefield) extends Observable{
  def createEmptyGrid(size: Int):Unit = {
    game = new Gamefield(new Grid(size.toInt),game.pile)
    notifyObservers
  }
  def setGrid(row: String, col: String, value: String): Unit ={
    if(!game.grid.isEmpty || (row == col && game.grid.size / 2 + 1 == row.toInt)) {
      game = new Gamefield(game.grid.set(row.toInt-1, col.toInt-1, value),game.pile)
      notifyObservers
    } else {
      println("First Cell to set have to be in Middle of the Grid")
    }
  }
  def createPile(equal:Int, plusminus:Int, muldiv:Int, blank:Int, digit:Int): Unit = {
    game = new Gamefield(game.grid, new Pile(equal,plusminus,muldiv,blank,digit))
    notifyObservers
  }
  def shufflePile():Unit = {
    game = Gamefield(game.grid, game.pile.shuffle, game.playerList)
    notifyObservers
  }
  def takeFromPile(name: String, size:Int): Unit = {
    shufflePile()
    if (game.playerList.contains(name)) {
      //game.playerList(name).addToHand(game.pile.take(size))
      game = Gamefield(game.grid, game.pile.drop(size), game.replacePlayer(name, game.playerList(name).addToHand(game.pile.take(size))))
      notifyObservers
  } else
      println("Player " + name + " doesn't exist")
  }

  def addPlayer(name: String): Unit ={
    game = Gamefield(game.grid, game.pile, game.createPlayer(name))
    notifyObservers
  }

  def removePlayer(name: String): Unit ={
    game = Gamefield(game.grid, game.pile, game.deletePlayer(name))
    notifyObservers
  }

  def fillAllHand(): Unit = {
    val nrLeftToFill: Iterable[Int] = game.playerList.values.map(player => player.maxHandSize - player.getNrCardsInHand)
    val playername: Iterable[String] = game.playerList.keys
    (playername,nrLeftToFill).zipped.foreach((p,n) => takeFromPile(p,n))
    notifyObservers
  }

  def showHand(): Unit = println(game.playerListToString)

  def gameToString: String = game.toString
}
