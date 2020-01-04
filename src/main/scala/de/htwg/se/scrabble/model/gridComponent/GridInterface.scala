package de.htwg.se.scrabble.model.gridComponent

import de.htwg.se.scrabble.model.gridComponent.cellComponent.CellInterface

trait GridInterface {
  def cell(row: Int, col: Int): CellInterface

  def isEmpty: Boolean

  def set(row: Int, col: Int, value: String): GridInterface

  def size: Int

  def getNeighborsOf(row: Int, col: Int): Map[(Int, Int), CellInterface]
}
