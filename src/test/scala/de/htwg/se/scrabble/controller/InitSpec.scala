package de.htwg.se.scrabble.controller

import com.google.inject.Guice
import de.htwg.se.scrabble.ScrabbleModule
import de.htwg.se.scrabble.controller.controllerComponent.ControllerInterface
import de.htwg.se.scrabble.controller.controllerComponent.GameStatus.Init
import de.htwg.se.scrabble.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl.GameFieldFixedSizeCreateStrategy
import org.scalatest.{Matchers, WordSpec}

import scala.util.Failure
class InitSpec extends WordSpec with Matchers{
  "init status" when {
    val init = Init()
    val injector = Guice.createInjector(new ScrabbleModule)
    val controller = injector.getInstance(classOf[ControllerInterface])
    "set grid" should {
      val res = init.setGrid(controller, 2, 2, 0)
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
