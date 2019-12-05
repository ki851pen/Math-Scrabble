package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.cell.Cell
import de.htwg.se.scrabble.model.gameField.{GameField, GameFieldFixedSizeCreateStrategy}
import org.scalatest.{Matchers, WordSpec}

class GameFieldStrategySpec extends WordSpec with Matchers{
  "A Gamefield" should {
    val gameFieldStrat = new GameFieldFixedSizeCreateStrategy()
    val gameField = gameFieldStrat.createNewGameField()
    "modified with specialcell" in {
      val grid = gameField.grid
      grid.cell(0,0).cellType shouldBe "t"
      grid.cell(1,1).cellType shouldBe "d"
      grid.cell(gameFieldStrat.sizeGrid-1, gameFieldStrat.sizeGrid-1).cellType shouldBe "t"
    }
  }
}
