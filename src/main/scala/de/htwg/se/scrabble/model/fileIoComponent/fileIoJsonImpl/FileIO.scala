package de.htwg.se.scrabble.model.fileIoComponent.fileIoJsonImpl

import de.htwg.se.scrabble.model.fileIoComponent.FileIOInterface
import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface

class FileIO extends FileIOInterface{
  override def load: GameFieldInterface = ???

  override def save(gameField: GameFieldInterface): Unit = ???
}
