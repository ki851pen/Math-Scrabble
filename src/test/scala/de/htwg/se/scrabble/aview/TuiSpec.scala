package de.htwg.se.scrabble.aview
import de.htwg.se.scrabble.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl.GameFieldFixedSizeCreateStrategy
import de.htwg.se.scrabble.model.gridComponent.cellComponent.cellBaseImpl.Cell
import de.htwg.se.scrabble.model.gridComponent.gridBaseImpl.Grid
import org.scalatest.{Matchers, WordSpec}

class TuiSpec extends WordSpec with Matchers{
  "A Math-Scrabble Tui" should {
    val controller = new Controller(new GameFieldFixedSizeCreateStrategy())
    val tui = new Tui(controller)
    "create and empty Grid on input 'init'" in {
      tui.processInputLine("init")
      controller.getGameField.grid should be(new Grid(15).initSpecialCell)
    }

    "create 9x9 empty Grid(one of the fixed size) on input 'gf 9'" in {
      tui.processInputLine("gf 9")
      controller.getGameField.grid should be(new Grid(9).initSpecialCell)
    }

    "create 6x6 empty Grid(free size) on input 'gf 6 1 1 1 1 1' and set the pile as desired" in {
      tui.processInputLine("gf 6 1 1 1 1 1")
      controller.getGameField.grid should be(new Grid(6).initSpecialCell)
    }
    "create a default pile on input 'p'" in {
      tui.processInputLine("p")
      controller.getGameField.pile.size should be(100)
      controller.getGameField.pile.toString should (include("+") and include("-") and  include("*") and include("/") and include("=") and include("?") and include("5"))
    }
    "shuffle a pile on input 's'" in {
      val oldPile = controller.getGameField.pile.tilepile
      tui.processInputLine("s")
      controller.getGameField.pile.size should be(100)
      controller.getGameField.pile.tilepile should not equal oldPile
    }
    "set a cell on input 'set 8 8 ='" in {
      tui.processInputLine("gf 15")
      tui.processInputLine("p 1 0 0 0 0")
      tui.processInputLine("fh")
      tui.processInputLine("set 8 8 =")
      controller.getGameField.grid.cell(7,7) shouldEqual Cell("t","=")
    }
    "set a pile and its size on input 'p 1 1 1 1 1'" in {
      tui.processInputLine("p 1 1 1 1 1")
      controller.getGameField.pile.size should be(16)
    }
    "do nothing on invalid input" in {
      tui.processInputLine("-")
      controller.getGameField should be (controller.getGameField)
    }
    "have a help in string form" in {
      tui.help shouldBe a [String]
    }
  }
}
