package de.htwg.se.scrabble.model.gameField

import de.htwg.se.scrabble.model.{Grid, Pile, Player}

case class GameField(grid: Grid, pile: Pile, playerList: Map[String,Player]) {
  def this(grid: Grid,pile: Pile) = this(grid, pile, Map("A" -> new Player("A")))
  def replacePlayer(oldPlayer:String, player: Player): Map[String, Player] = playerList - oldPlayer + (player.toString -> player)
  def createPlayer(name:String): Map[String, Player] = playerList + (name -> new Player(name))
  def deletePlayer(name:String): Map[String, Player] = playerList - name
  def playerListToString: String = playerList.values.map(player => "Player " + player.name + " has: " + player.hand.mkString(", "))
    .mkString("\n")
  override def toString: String = "Grid :" + grid.toString + "\nPile: " + pile.toString + "\n" + playerListToString
}
