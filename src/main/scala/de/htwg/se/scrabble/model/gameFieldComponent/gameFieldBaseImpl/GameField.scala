package de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl

import com.google.inject.Inject
import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gridComponent.GridInterface
import de.htwg.se.scrabble.model.gridComponent.gridBaseImpl.Grid
import de.htwg.se.scrabble.model.pileComponent.PileBaseImpl.Pile
import de.htwg.se.scrabble.model.pileComponent.PileInterface
import de.htwg.se.scrabble.model.playerComponent.playerBaseImpl.Player

case class GameField @Inject() (grid: GridInterface, pile: PileInterface, playerList: Map[String, Player]) extends GameFieldInterface {
  def this(grid: Grid, pile: Pile) = this(grid, pile, Map("A" -> new Player("A"), "B" -> new Player("B")))

  private def changePlayerAttr(name: String, changedPlayer: Player): Map[String, Player] = playerList.updated(name, changedPlayer)

  def gameToStringWOPlayer: String = "Grid :" + grid.toString + "\nPile: " + pile.size.toString + "\n" +
    playerList.values.map(p => p.name + "'s points: " + p.point.toString).mkString("    ") + "\n"

  def createNewPile(equal: Int, plusminus: Int, muldiv: Int, blank: Int, digit: Int): GameField = copy(pile = new Pile(equal, plusminus, muldiv, blank, digit))

  def shufflePile: GameField = copy(pile = pile.shuffle)

  def fillHand(playerName: String, howMany: Int): GameField = {
    copy(pile = pile.drop(howMany), playerList = changePlayerAttr(playerName, playerList(playerName).addToHand(pile.take(howMany))))
  }

  def clearHand(playerName: String): GameField = {
    copy(pile = Pile(pile.tilepile ::: playerList(playerName).hand), playerList = changePlayerAttr(playerName, playerList(playerName).dropAllCard))
  }

  def playerPlay(player: String, row: Int, col: Int, index: Int): GameField = {
    val card = playerList(player).hand(index)
    copy(grid = grid.set(row, col, card.toString), playerList = changePlayerAttr(player, playerList(player).useCard(card)))
  }

  def calPointForPlayer(playerName: String, currentSum: Int): GameField =
    copy(playerList = changePlayerAttr(playerName, playerList(playerName).addPoint(currentSum)))

  def playerListToString: String = playerList.values.map(_.toString).mkString("\n")

  //def renamePlayer(playerName: String, newName: String): GameField = copy(playerList = changePlayerAttr(playerName, playerList(playerName).rename(newName)))

  def gameToString(name: String): String = gameToStringWOPlayer + playerList(name).toString

  override def toString: String = gameToStringWOPlayer + playerListToString

}
