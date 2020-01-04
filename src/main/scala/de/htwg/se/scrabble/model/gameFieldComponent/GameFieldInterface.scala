package de.htwg.se.scrabble.model.gameFieldComponent

import de.htwg.se.scrabble.model.gridComponent.GridInterface
import de.htwg.se.scrabble.model.playerComponent.PlayerInterface
import de.htwg.se.scrabble.model.pileComponent.PileInterface

trait GameFieldInterface {
  def pile: PileInterface

  def grid: GridInterface

  def playerList: Map[String, PlayerInterface]

  def createNewPile(equal: Int, plusminus: Int, muldiv: Int, blank: Int, digit: Int): GameFieldInterface

  def shufflePile: GameFieldInterface

  def fillHand(playerName: String, howMany: Int): GameFieldInterface

  def clearHand(playername: String): GameFieldInterface

  //def changePlayerAttr(name: String, changedPlayer: Player): Map[String, PlayerInterface]
  def playerPlay(player: String, row: Int, col: Int, index: Int): GameFieldInterface

  def calPointForPlayer(playerName: String, currentSum: Int): GameFieldInterface

  def gameToStringWOPlayer: String

  def playerListToString: String

  //def renamePlayer(playerName: String, newName: String): GameFieldInterface

  def gameToString(name: String): String
}

