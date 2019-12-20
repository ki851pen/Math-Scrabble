package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.controller.GameStatus.FirstCard
import de.htwg.se.scrabble.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl.GameFieldFixedSizeCreateStrategy
import org.scalatest.{Matchers, WordSpec}
class FirstCardSpec extends WordSpec with Matchers{
  "FirstCard status" when {
    val firstCard = FirstCard()
    val controller = new Controller(new GameFieldFixedSizeCreateStrategy(5))
    "set grid" should {
      val res = firstCard.setGrid(controller, 2, 2, 0)
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
