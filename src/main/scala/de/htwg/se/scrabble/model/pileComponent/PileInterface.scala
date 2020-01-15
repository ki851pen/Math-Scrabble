package de.htwg.se.scrabble.model.pileComponent

import de.htwg.se.scrabble.model.gridComponent.CardInterface

trait PileInterface {

  def tilepile: List[CardInterface]

  def size: Int

  def shuffle: PileInterface

  def drop(value: Int): PileInterface

  def take(value: Int): List[CardInterface]

  def add(value: String, howMany: Int): PileInterface
}