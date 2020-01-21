package de.htwg.se.scrabble.controller

import com.google.inject.Guice
import de.htwg.se.scrabble.ScrabbleModule
import de.htwg.se.scrabble.controller.controllerComponent.ControllerInterface
import de.htwg.se.scrabble.controller.controllerComponent.GameStatus.FirstCard
import org.scalatest.{Matchers, WordSpec}
class FirstCardSpec extends WordSpec with Matchers{
  "FirstCard status" when {
    val firstCard = FirstCard()
    val injector = Guice.createInjector(new ScrabbleModule)
    val controller = injector.getInstance(classOf[ControllerInterface])
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
