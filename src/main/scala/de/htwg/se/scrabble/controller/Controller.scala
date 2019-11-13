package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.model.{Gamefield, Grid, Pile}
import de.htwg.se.scrabble.util.Observable

class Controller(var game: Gamefield) extends Observable{
  def createEmptyGrid(size: String):Unit = {
    if (size.toIntOption.getOrElse("") == "") {
      println("size have to be integer")
    } else {
      game = new Gamefield(new Grid(size.toInt),game.pile)
      notifyObservers
    }
  }
  def setGrid(row: String, col: String, value: String): Unit ={
    if(!game.grid.isEmpty || (row == col && game.grid.size/2+1 == row.toInt)) {
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
    game = new Gamefield(game.grid, game.pile.shuffle)
    notifyObservers
  }

  def gameToString: String = game.toString
}
