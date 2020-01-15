package de.htwg.se.scrabble.model.fileIoComponent

import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface

trait FileIOInterface {
  def load: GameFieldInterface
  def save(gameField: GameFieldInterface): Unit
}
