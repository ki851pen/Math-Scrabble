package de.htwg.se.scrabble.model.gameField

import de.htwg.se.scrabble.model.cellComponent.Cell
import de.htwg.se.scrabble.model.{Grid, Pile}

trait GameFieldCreateStrategyTemplate {
  def sizeGrid: Int
  def pile: Pile
  def createNewGameField: GameField ={
    var gameField = new GameField(new Grid(sizeGrid), pile)
    gameField = gameField.copy(grid = gameField.grid.initSpecialCell)
    gameField
  }
}