package de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl

import de.htwg.se.scrabble.model.gameFieldComponent.gridComponent.gridBaseImpl.Grid
import de.htwg.se.scrabble.model.gameFieldComponent.pileComponent.PileBaseImpl.Pile

trait GameFieldCreateStrategyTemplate {
  def sizeGrid: Int

  def pile: Pile

  def createNewGameField: GameField = {
    var gameField = new GameField(new Grid(sizeGrid), pile)
    gameField = gameField.copy(grid = gameField.grid.initSpecialCell)
    gameField
  }
}
