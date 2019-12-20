package de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl

import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.scrabble.model.pileComponent.PileBaseImpl.Pile
import de.htwg.se.scrabble.model.playerComponent.playerBaseImpl.Player

case class GameField(grid: Grid, pile: Pile, playerList: Map[String, Player]) extends GameFieldInterface {
  def this(grid: Grid, pile: Pile) = this(grid, pile, Map("A" -> new Player("A"), "B" -> new Player("B")))

  def changePlayerAttr(name: String, changedPlayer: Player): Map[String, Player] = playerList.updated(name, changedPlayer)

  def gameToStringWOPlayer: String = "Grid :" + grid.toString + "\nPile: " + pile.size.toString + "\n" +
    playerList.values.map(p => p.name + "'s points: " + p.point.toString).mkString("    ") + "\n"

  def playerListToString: String = playerList.values.map(_.toString).mkString("\n")

  def gameToString(name: String): String = gameToStringWOPlayer + playerList(name).toString

  override def toString: String = gameToStringWOPlayer + playerListToString
}
