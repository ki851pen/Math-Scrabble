package de.htwg.se.scrabble.model

case class Gamefield(grid: Grid, pile: Pile, playerList: List[Player]) {
  def this(grid: Grid,pile: Pile) = this(grid,pile,List(Player("A")))
  def createPlayer(name:String): List[Player] = Player(name) :: playerList
  override def toString: String = "Grid :" + grid.toString + "\n Pile: " + pile.toString + "\n"
}

