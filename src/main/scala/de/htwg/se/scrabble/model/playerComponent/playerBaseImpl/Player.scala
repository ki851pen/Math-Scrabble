package de.htwg.se.scrabble.model.playerComponent.playerBaseImpl

import de.htwg.se.scrabble.model.gridComponent.CardInterface
import de.htwg.se.scrabble.model.playerComponent.PlayerInterface

case class Player(name: String, hand: List[CardInterface], point: Int) extends PlayerInterface {
  def this(name: String) = this(name, Nil, 0)

  val maxHandSize: Int = 9

  def getNrCardsInHand: Int = hand.size

  def addToHand(cards: List[CardInterface]): Player = copy(hand = hand ::: cards)

  def dropAllCard: Player = copy(hand = Nil)

  def useCard(card: CardInterface): Player = copy(hand = hand diff List(card))

  def addPoint(pointToAdd: Int): Player = copy(point = point + pointToAdd)

  def rename(newName: String): Player = copy(name = newName)
  override def toString: String = name + " has: " + hand.mkString(", ")

}
