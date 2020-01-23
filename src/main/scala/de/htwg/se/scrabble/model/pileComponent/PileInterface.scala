package de.htwg.se.scrabble.model.pileComponent

import de.htwg.se.scrabble.model.gridComponent.CardInterface
import de.htwg.se.scrabble.model.gridComponent.gridBaseImpl.Card

trait PileInterface {

  def tilepile: List[CardInterface]

  def size: Int

  def shuffle: PileInterface

  def drop(value: Int): PileInterface

  def take(value: Int): List[Card]

  def add(value: String, howMany: Int): PileInterface
}
