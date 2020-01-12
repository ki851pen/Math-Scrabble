package de.htwg.se.scrabble.model.gameFieldComponent.playerComponent

import de.htwg.se.scrabble.model.gameFieldComponent.gridComponent.cellComponent.CardInterface
import de.htwg.se.scrabble.model.gameFieldComponent.gridComponent.cellComponent.cellBaseImpl.Card


trait PlayerInterface {
  def hand: List[CardInterface]

  def name: String

  def maxHandSize: Int

  def point: Int

  def getNrCardsInHand: Int

  def addToHand(cards: List[Card]): PlayerInterface

  def dropAllCard: PlayerInterface

  def useCard(card: Card): PlayerInterface

  def addPoint(pointToAdd: Int): PlayerInterface

  def rename(newName: String): PlayerInterface
}
