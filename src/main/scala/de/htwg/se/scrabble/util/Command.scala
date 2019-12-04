package de.htwg.se.scrabble.util

trait Command {

  def doStep:Unit
  def undoStep:Unit
  def redoStep:Unit

}
