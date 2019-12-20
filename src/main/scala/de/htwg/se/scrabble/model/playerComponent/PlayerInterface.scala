package de.htwg.se.scrabble.model.playerComponent

import de.htwg.se.scrabble.model.gridComponent.cellComponent.CardInterface
import de.htwg.se.scrabble.model.gridComponent.cellComponent.cellBaseImpl.Card


trait PlayerInterface {
  def hand: List[CardInterface]

  def point: Int

  def getNrCardsInHand: Int

  def addToHand(cards: List[Card]): PlayerInterface

  def useCard(card: Card): PlayerInterface
}
