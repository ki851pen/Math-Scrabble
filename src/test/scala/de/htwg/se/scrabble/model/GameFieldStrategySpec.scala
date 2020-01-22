package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl.GameFieldFixedSizeCreateStrategy
import org.scalatest.{Matchers, WordSpec}

class GameFieldStrategySpec extends WordSpec with Matchers{
  "A GameField create with CreateStrategy" should {
    val gameFieldStrat = new GameFieldFixedSizeCreateStrategy()
    val gameField = gameFieldStrat.createNewGameField
    "modified with specialcell" in {
      val grid = gameField.grid
      grid.cell(0,0).isTriple shouldBe true
      grid.cell(1,1).isDouble shouldBe true
      grid.cell(gameFieldStrat.sizeGrid-1, gameFieldStrat.sizeGrid-1).isTriple shouldBe true
    }
  }
  "A GameField create by different size CreateStrategy" should {
    val gameFieldStrat3 = new GameFieldFixedSizeCreateStrategy(3)
    val gameFieldStrat5 = new GameFieldFixedSizeCreateStrategy(5)
    val gameFieldStrat9 = new GameFieldFixedSizeCreateStrategy(9)
    val gameField3 = gameFieldStrat3.createNewGameField
    val gameField5 = gameFieldStrat5.createNewGameField
    val gameField9 = gameFieldStrat9.createNewGameField
    "have a different grid size" in {
      gameField3.grid.size shouldBe 3
      gameField5.grid.size shouldBe 5
      gameField9.grid.size shouldBe 9
    }
    "have a different pile size" in {
      gameField3.pile.size shouldBe 21
      gameField5.pile.size shouldBe 40
      gameField9.pile.size shouldBe 63
    }
  }
}
