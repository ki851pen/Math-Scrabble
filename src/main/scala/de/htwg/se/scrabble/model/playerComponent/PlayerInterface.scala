package de.htwg.se.scrabble.model.playerComponent

import de.htwg.se.scrabble.model.gridComponent.CardInterface

trait PlayerInterface {
  def hand: List[CardInterface]

  def name: String

  def maxHandSize: Int

  def point: Int

  def getNrCardsInHand: Int

  def addToHand(cards: List[CardInterface]): PlayerInterface

  def dropAllCard: PlayerInterface

  def useCard(card: CardInterface): PlayerInterface

  def addPoint(pointToAdd: Int): PlayerInterface

  def rename(newName: String): PlayerInterface
}
