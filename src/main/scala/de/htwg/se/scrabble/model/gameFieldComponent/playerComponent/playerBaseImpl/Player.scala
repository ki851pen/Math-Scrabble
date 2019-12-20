package de.htwg.se.scrabble.model.gameFieldComponent.playerComponent.playerBaseImpl

import de.htwg.se.scrabble.model.gameFieldComponent.gridComponent.cellComponent.cellBaseImpl.Card
import de.htwg.se.scrabble.model.gameFieldComponent.playerComponent.PlayerInterface

case class Player(name: String, hand: List[Card], point: Int) extends PlayerInterface {
  def this(name: String) = this(name, Nil, 0)

  val maxHandSize: Int = 9

  def getNrCardsInHand: Int = hand.size

  def addToHand(cards: List[Card]): Player = copy(hand = hand ::: cards)

  def useCard(card: Card): Player = copy(hand = hand diff List(card))

  override def toString: String = name + " has: " + hand.mkString(", ")

}
