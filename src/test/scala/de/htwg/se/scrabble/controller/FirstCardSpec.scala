package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.controller.GameStatus.{FirstCard, Init}
import de.htwg.se.scrabble.model.gameField.GameFieldFixedSizeCreateStrategy
import org.scalatest.{Matchers, WordSpec}

import scala.util.Failure
class FirstCardSpec extends WordSpec with Matchers{
  "FirstCard status" when {
    val firstCard = FirstCard()
    val controller = new Controller(new GameFieldFixedSizeCreateStrategy(5))
    "set grid" should {
      val res = firstCard.setGrid(controller, 2, 2, "")
    }
    "calculate point" should{
      val res = firstCard.calPoint(controller, 0)
      "not work and return None" in {
        res shouldBe None
      }
    }
    "use gameToString" should {
      val res = firstCard.gameToString(controller)
      "return instruction as String" in {
        res shouldBe controller.getGameField.gameToString("A")
      }
    }
  }
}
