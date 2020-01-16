package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl.GameFieldFixedSizeCreateStrategy
import org.scalatest.{Matchers, WordSpec}

class GameFieldStrategySpec extends WordSpec with Matchers{
  "A Gamefield" should {
    val gameFieldStrat = new GameFieldFixedSizeCreateStrategy()
    val gameField = gameFieldStrat.createNewGameField
    "modified with specialcell" in {
      val grid = gameField.grid
      grid.cell(0,0).isTriple shouldBe true
      grid.cell(1,1).isDouble shouldBe true
      grid.cell(gameFieldStrat.sizeGrid-1, gameFieldStrat.sizeGrid-1).isTriple shouldBe true
    }
  }
}
