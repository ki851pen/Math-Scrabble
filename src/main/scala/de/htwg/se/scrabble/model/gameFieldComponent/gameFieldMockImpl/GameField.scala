package de.htwg.se.scrabble.model.gameFieldComponent.gameFieldMockImpl

import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gameFieldComponent.gridComponent.gridBaseImpl.Grid
import de.htwg.se.scrabble.model.gameFieldComponent.pileComponent.PileBaseImpl.Pile
import de.htwg.se.scrabble.model.gameFieldComponent.playerComponent.PlayerInterface
import de.htwg.se.scrabble.model.gameFieldComponent.playerComponent.playerBaseImpl.Player

case class GameField(grid: Grid, pile: Pile, playerList: Map[String, Player]) extends GameFieldInterface {
  override def changePlayerAttr(name: String, changedPlayer: Player): Map[String, PlayerInterface] = ???

  override def gameToStringWOPlayer: String = ""

  override def playerListToString: String = ""

  override def gameToString(name: String): String = ""
}
