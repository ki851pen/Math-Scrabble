package de.htwg.se.scrabble.model.gameFieldComponent.gameFieldMockImpl

import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gameFieldComponent.gridComponent.cellComponent.cellBaseImpl.Card
import de.htwg.se.scrabble.model.gameFieldComponent.gridComponent.gridBaseImpl.Grid
import de.htwg.se.scrabble.model.gameFieldComponent.pileComponent.PileBaseImpl.Pile
import de.htwg.se.scrabble.model.gameFieldComponent.playerComponent.playerBaseImpl.Player

case class GameField(grid: Grid, pile: Pile, playerList: Map[String, Player]) extends GameFieldInterface {
  //def changePlayerAttr(name: String, changedPlayer: Player): Map[String, PlayerInterface] = ???

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
