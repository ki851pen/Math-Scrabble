package de.htwg.se.scrabble.aview
import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.{Grid, Pile}
import de.htwg.se.scrabble.model.cell.Cell
import de.htwg.se.scrabble.model.gameField.GameFieldFixedSizeCreateStrategy
import org.scalatest.{Matchers, WordSpec}

class TuiSpec extends WordSpec with Matchers{
  "A Math-Scrabble Tui" should {
    val controller = new Controller(new GameFieldFixedSizeCreateStrategy())
    val tui = new Tui(controller)
    "create and empty Grid on input 'g'" in {
      tui.processInputLine("g")
      controller.getGameField.grid should be(new Grid(15))
    }

    "create 9x9 empty Grid(one of the fixed size) on input 'gf 9'" in {
      tui.processInputLine("gf 9")
      controller.getGameField.grid should be(new Grid(9))
    }

    "create 6x6 empty Grid(free size) on input 'gf 6 1 1 1 1 1' and set the pile as desired" in {
      tui.processInputLine("gf 6 1 1 1 1 1")
      controller.getGameField.grid should be(new Grid(6))
      controller.getGameField.pile.size should be(16)
    }
    "create a default pile on input 'p'" in {
      tui.processInputLine("p")
      controller.getGameField.pile.size should be(100)
      controller.getGameField.pile.toString should (include("+") and include("-") and  include("*") and include("/") and include("=") and include("?") and include("5"))
    }
    "shuffle a pile on input 's'" in {
      val oldpile = controller.getGameField.pile.tilepile
      tui.processInputLine("s")
      controller.getGameField.pile.size should be(100)
      controller.getGameField.pile.tilepile should not equal oldpile
    }
    "add player B on input 'py+ B'" in {
      tui.processInputLine("py+ B")
      controller.getGameField.playerList.keys should contain ("B")
      controller.getGameField.playerList.size should be (2)
    }
    "remove player B on input 'py- B'" in {
      tui.processInputLine("py- B")
      controller.getGameField.playerList.keys should not contain "B"
      controller.getGameField.playerList.size should be (1)
    }
    "set a cell on input 'A 8 8 3'" in {
      tui.processInputLine("g")
      tui.processInputLine("p 1 0 0 0 0")
      tui.processInputLine("fh")
      tui.processInputLine("A 8 8 =")
      controller.getGameField.grid.cell(7,7) shouldEqual Cell("=")
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
