package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.model.{Gamefield, Grid}
import de.htwg.se.scrabble.util.Observable

class Controller(var game: Gamefield) extends Observable{
  def createEmptyGrid(size: Int):Unit = {
    game = Gamefield(new Grid(size),game.pile)
    notifyObservers
  }

  def gameToString: String = game.toString
}
