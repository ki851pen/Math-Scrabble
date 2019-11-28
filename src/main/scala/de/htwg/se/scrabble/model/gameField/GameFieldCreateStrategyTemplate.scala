package de.htwg.se.scrabble.model.gameField

import de.htwg.se.scrabble.model.cell.Cell
import de.htwg.se.scrabble.model.{Grid, Pile}

trait GameFieldCreateStrategyTemplate {
  def sizeGrid: Int
  def pile: Pile
  def createNewGameField(): GameField ={
    var gameField = new GameField(new Grid(sizeGrid), pile)
    gameField = gameField.copy(grid = initSpecialCell(gameField.grid))
    gameField
  }

  def initSpecialCell(grid: Grid):Grid ={
    val max: Int = sizeGrid - 1
    val half: Int = max / 2
    Grid(Vector.tabulate(sizeGrid,sizeGrid){(row,col) => (row,col) match {
      case (a,b) if (a==0 | a ==max | a ==half) & (b==0 | b ==max | b==half) => Cell("t","")
      case (a,b) if a == b || a == max-b => Cell("d","")
      case _ => Cell("n","")
    }})
  }
}