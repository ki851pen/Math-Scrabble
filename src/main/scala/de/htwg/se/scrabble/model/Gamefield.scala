package de.htwg.se.scrabble.model

case class Gamefield(grid: Grid, pile: Pile, playerList: Map[String,Player]) {
  def this(grid: Grid,pile: Pile) = this(grid,pile, Map("A" -> new Player("A")))
  def replacePlayer(name:String, player: Player): Map[String, Player] = playerList.updated(name, player)
  def createPlayer(name:String): Map[String, Player] =  playerList + (name -> new Player(name))
  def deletePlayer(name:String): Map[String, Player] = playerList - name
  override def toString: String = "Grid :" + grid.toString + "\nPile: " + pile.toString + "\n" + playerList.keys.map(key => key +": "+ playerList(key).getHand)
}
