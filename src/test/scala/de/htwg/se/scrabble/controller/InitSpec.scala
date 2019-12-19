package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.controller.GameStatus.Init
import de.htwg.se.scrabble.controller.controllerComponent.Controller
import de.htwg.se.scrabble.model.gameField.GameFieldFixedSizeCreateStrategy
import org.scalatest.{Matchers, WordSpec}

import scala.util.Failure
class InitSpec extends WordSpec with Matchers{
  "init status" when {
    val init = Init()
    val controller = new Controller(new GameFieldFixedSizeCreateStrategy(5))
    "set grid" should {
      val res = init.setGrid(controller, 2, 2, "")
      "return failure" in {
        res shouldBe a [Failure[_]]
      }
    }
    "calculate point" should{
      val res = init.calPoint(controller, 0)
      "not work and return None" in {
        res shouldBe None
      }
    }
    "use gameToString" should {
      val res = init.gameToString(controller)
      "return instruction as String" in {
        res shouldBe "write init or gf [size] to begin"
      }
    }
  }
}
