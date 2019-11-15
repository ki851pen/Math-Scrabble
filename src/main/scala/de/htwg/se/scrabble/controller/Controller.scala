package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.model.{Gamefield, Grid, Pile}
import de.htwg.se.scrabble.util.Observable

class Controller(var game: Gamefield) extends Observable{
  def createEmptyGrid(size: Int):Unit = {
    game = game.copy(grid = new Grid(size.toInt))
    notifyObservers
  }
  def setGrid(row: String, col: String, value: String): Unit ={
    if(!game.grid.isEmpty || (row == col && game.grid.size / 2 + 1 == row.toInt)) {
      game = game.copy(grid = game.grid.set(row.toInt-1, col.toInt-1, value))
      notifyObservers
    } else {
      println("First Cell to set have to be in Middle of the Grid")
    }
  }
  def createPile(equal:Int, plusminus:Int, muldiv:Int, blank:Int, digit:Int): Unit = {
    game = game.copy(pile = new Pile(equal,plusminus,muldiv,blank,digit))
    notifyObservers
  }
  def shufflePile():Unit = {
    game = game.copy(pile = game.pile.shuffle)
    notifyObservers
  }
  def takeFromPile(name: String, size:Int): Unit = {
    if (game.playerList.contains(name)) {
      shufflePile()
      game = Gamefield(game.grid, game.pile.drop(size), game.replacePlayer(name, game.playerList(name).addToHand(game.pile.take(size))))
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
    game = game.copy(playerList = game.createPlayer(name))
    notifyObservers
  }

  def removePlayer(name: String): Unit ={
    game = game.copy(playerList = game.deletePlayer(name))
    notifyObservers
  }

  def fillAllHand(): Unit = {
    val nrLeftToFill: Iterable[Int] = game.playerList.values.map(player => player.maxHandSize - player.getNrCardsInHand)
    val playername: Iterable[String] = game.playerList.keys
    (playername,nrLeftToFill).zipped.foreach((p,n) => takeFromPile(p,n))
    notifyObservers
  }

  def gameToString: String = game.toString
}
