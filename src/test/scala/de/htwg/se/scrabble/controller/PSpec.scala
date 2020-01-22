package de.htwg.se.scrabble.controller

import com.google.inject.Guice
import de.htwg.se.scrabble.ScrabbleModule
import de.htwg.se.scrabble.controller.controllerComponent.ControllerInterface
import de.htwg.se.scrabble.controller.controllerComponent.GameStatus.P
import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import org.scalatest.{Matchers, WordSpec}

class PSpec extends WordSpec with Matchers{
  "P[player_name] status" should {
    val p = P("B")
    "have string representation" in {
      p.toString shouldBe "pB"
    }
  }
  "P[player_name] status" when {
    val p = P("A")
    val injector = Guice.createInjector(new ScrabbleModule)
    val controller = injector.getInstance(classOf[ControllerInterface])
    controller.init
    controller.setGrid(7, 7, 0)
    "set grid at different cell" should {
      val res = p.setGrid(controller, 7, 8, 0)
      "work" in {
        res.isSuccess shouldBe true
      }
    }
    "set grid at already set cell" should {
      val res = p.setGrid(controller, 7, 7, 0)
      "not work" in {
        res.isFailure shouldBe true
      }
    }
    "set grid at faraway cell" should {
      val res = p.setGrid(controller, 0, 0, 0)
      "not work" in {
        res.isFailure shouldBe true
      }
    }
    "calculate point" should{
      val res = p.calPoint(controller, 0)
      "work" in {
        res.get shouldBe a [GameFieldInterface]
      }
    }
    "use gameToString" should {
      val res = p.gameToString(controller)
      "return instruction as String" in {
        res shouldBe controller.getGameField.gameToString("A")
      }
    }
    "get new cells" should {
      val res = p.getNewCells
      "has a list of position of the cells" in {
        res shouldBe a [List[(Int, Int)]]
      }
    }
  }
}
