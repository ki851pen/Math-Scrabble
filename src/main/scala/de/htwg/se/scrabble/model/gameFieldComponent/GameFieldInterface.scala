package de.htwg.se.scrabble.model.gameFieldComponent

import de.htwg.se.scrabble.model.gameFieldComponent.pileComponent.PileInterface
import de.htwg.se.scrabble.model.gameFieldComponent.playerComponent.PlayerInterface
import de.htwg.se.scrabble.model.gameFieldComponent.playerComponent.playerBaseImpl.Player

trait GameFieldInterface {
  def pile: PileInterface

  def playerList: Map[String, PlayerInterface]

  def changePlayerAttr(name: String, changedPlayer: Player): Map[String, PlayerInterface]

  def gameToStringWOPlayer: String

  def playerListToString: String

  def gameToString(name: String): String
}

