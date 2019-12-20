package de.htwg.se.scrabble.model.gameFieldComponent.gridComponent

import de.htwg.se.scrabble.model.gameFieldComponent.gridComponent.cellComponent.CellInterface

trait GridInterface {
  def cell(row: Int, col: Int): CellInterface

  def isEmpty: Boolean

  def set(row: Int, col: Int, value: String): GridInterface

  def size: Int
}
