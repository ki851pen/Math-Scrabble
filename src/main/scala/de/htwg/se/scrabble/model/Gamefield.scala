package de.htwg.se.scrabble.model

case class Gamefield(grid: Grid, pile: Pile) {
  override def toString: String = "Grid :" + grid.toString + "\n Pile: " + pile.toString + "\n"
}

