package de.htwg.se.scrabble.model.gameFieldComponent.gameFieldMockImpl

import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.scrabble.model.pileComponent.PileBaseImpl.Pile
import de.htwg.se.scrabble.model.playerComponent.PlayerInterface

case class GameField (grid: Grid, pile: Pile, playerList: Map[String, PlayerInterface]) extends GameFieldInterface {
 def gameToStringWOPlayer: String = ""

  def playerListToString: String = ""

  def gameToString(name: String): String = ""

  def createNewPile(equal: Int, plusminus: Int, muldiv: Int, blank: Int, digit: Int): GameFieldInterface = ???

  def shufflePile: GameFieldInterface = ???

  def fillHand(playerName: String, howMany: Int): GameFieldInterface = ???

  def clearHand(playername: String): GameFieldInterface = ???

  def playerPlay(player: String, row: Int, col: Int, index: Int): GameFieldInterface = ???

  def calPointForPlayer(playerName: String, currentSum: Int): GameFieldInterface = ???

  def renamePlayer(playerName: String, newName: String): GameFieldInterface = ???
}
