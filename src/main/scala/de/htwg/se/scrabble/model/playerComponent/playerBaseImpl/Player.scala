package de.htwg.se.scrabble.model.playerComponent.playerBaseImpl

import de.htwg.se.scrabble.model.gridComponent.gridBaseImpl.Card
import de.htwg.se.scrabble.model.playerComponent.PlayerInterface
import play.api.libs.json.Json

case class Player(name: String, hand: List[Card], point: Int) extends PlayerInterface {
  def this(name: String) = this(name, Nil, 0)

  val maxHandSize: Int = 10

  def getNrCardsInHand: Int = hand.size

  def addToHand(cards: List[Card]): Player = copy(hand = hand ::: cards)

  def dropAllCard: Player = copy(hand = Nil)

  def useCard(card: Card): Player = copy(hand = hand diff List(card))

  def addPoint(pointToAdd: Int): Player = copy(point = point + pointToAdd)

  def rename(newName: String): Player = copy(name = newName)

  override def toString: String = name + " has: " + hand.mkString(", ")

}

object Player {
  implicit var playerFormat = Json.format[Player]
}
