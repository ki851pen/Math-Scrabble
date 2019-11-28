package de.htwg.se.scrabble.model

trait GameFieldCreateStrategyTemplate {
  def sizeGrid: Int
  def pile: Pile
  def createNewGameField(): GameField ={
    var gameField = new GameField(new Grid(sizeGrid), pile)
    gameField = gameField.copy(grid = initSpecialCell(gameField.grid))
    gameField
  }

  //private def createGrid(sizeGrid: Int): Grid = new Grid(sizeGrid)

  def initSpecialCell(grid: Grid):Grid ={
    grid
  }
}
