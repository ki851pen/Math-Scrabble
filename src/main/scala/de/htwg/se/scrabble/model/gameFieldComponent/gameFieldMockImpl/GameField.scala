package de.htwg.se.scrabble.model.gameFieldComponent.gameFieldMockImpl

import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.scrabble.model.pileComponent.PileBaseImpl.Pile
import de.htwg.se.scrabble.model.playerComponent.playerBaseImpl.Player

case class GameField (grid: Grid, pile: Pile, playerList: Map[String, Player]) extends GameFieldInterface {
 def gameToStringWOPlayer: String = ""

  def playerListToString: String = ""

  def gameToString(name: String): String = ""

 def createNewPile(equal: Int, plusminus: Int, muldiv: Int, digit: Int): GameFieldInterface = this

 def shufflePile: GameFieldInterface = this

 def fillHand(playerName: String, howMany: Int): GameFieldInterface = this

 def clearHand(playername: String): GameFieldInterface = this

 def playerPlay(player: String, row: Int, col: Int, index: Int): GameFieldInterface = this

 def calPointForPlayer(playerName: String, currentSum: Int): GameFieldInterface = this
}
