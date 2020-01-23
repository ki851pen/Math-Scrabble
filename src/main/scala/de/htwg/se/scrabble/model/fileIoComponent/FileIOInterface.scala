package de.htwg.se.scrabble.model.fileIoComponent

import de.htwg.se.scrabble.util.Memento

trait FileIOInterface {
  def load: Memento
  def save(mem: Memento): Unit
}
