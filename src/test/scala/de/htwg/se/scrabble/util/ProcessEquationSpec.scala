package de.htwg.se.scrabble.util

import com.google.inject.Guice
import de.htwg.se.scrabble.ScrabbleModule
import de.htwg.se.scrabble.controller.controllerComponent.ControllerInterface
import de.htwg.se.scrabble.model.gridComponent.gridBaseImpl.Cell
import org.scalatest.{Matchers, WordSpec}

class ProcessEquationSpec extends WordSpec with Matchers {
  "util class process equation" should {
    val injector = Guice.createInjector(new ScrabbleModule)
    val controller = injector.getInstance(classOf[ControllerInterface])
    val p = ProcessEquation(controller)
    val input: List[Seq[Cell]] = List(Vector(Cell("1"),Cell("="),Cell("1"),Cell(""),Cell("3")))
    val wronginput: List[Seq[Cell]] = List(Vector(Cell("1"),Cell("="),Cell("1"),Cell("+"),Cell("3")))
    val wronginput2: List[Seq[Cell]] = List(Vector(Cell("1"),Cell(""),Cell("1"),Cell("+"),Cell("3")))
    "find equation from list of Seq of cell" in {
       p.findEquation(input) shouldBe List(Vector(Cell("1"),Cell("="),Cell("1")))
    }
    "evaluate equations correctly" in {
      val equa = p.findEquation(input)
      val wrong_equa = p.findEquation(wronginput)
      val wrong_equa2 = p.findEquation(wronginput2)
      equa.forall(p.evalEquation) shouldBe true
      wrong_equa.forall(p.evalEquation) shouldBe false
      wrong_equa2.forall(p.evalEquation) shouldBe false
    }
  }
}
