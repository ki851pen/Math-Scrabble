package de.htwg.se.scrabble.aview
import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.{Gamefield, Grid, Pile}
import org.scalatest.{Matchers, WordSpec}

class TuiSpec extends WordSpec with Matchers{
  "A Math-Scrabble Tui" should {
    val controller = new Controller(new Gamefield(new Grid(15), new Pile()))
    val tui = new Tui(controller)
    "create and empty Grid on input 'g'" in {
      tui.processInputLine("g")
      controller.game.grid should be(new Grid(15))
    }
    "create 5x5 empty Grid on input 'g 5'" in {
      tui.processInputLine("g 5")
      controller.game.grid should be(new Grid(5))
    }
    "create a default pile on input 'p'" in {
      tui.processInputLine("p")
      controller.game.pile.size should be(100)
      controller.game.pile.toString should (include("+") and include("-") and  include("*") and include("/") and include("=") and include("_") and include("5"))
    }
    "shuffle a pile on input 's'" in {
      val oldpile = controller.game.pile.tilepile
      tui.processInputLine("s")
      controller.game.pile.size should be(100)
      controller.game.pile.tilepile should not equal oldpile
    }
    "add player B on input 'py+ B'" in {
      tui.processInputLine("py+ B")
      controller.game.playerList.keys should contain ("B")
      controller.game.playerList.size should be (2)
    }
    "remove player B on input 'py- B'" in {
      tui.processInputLine("py- B")
      controller.game.playerList.keys should not contain "B"
      controller.game.playerList.size should be (1)
    }
    "set a cell on input '8 8 3'" in {
      tui.processInputLine("g")
      tui.processInputLine("8 8 3")
      controller.game.grid.cell(7,7).value should be("3")
    }
    "set a pile and its size on input 'p 1 1 1 1 1'" in {
      tui.processInputLine("p 1 1 1 1 1")
      controller.game.pile.size should be(16)
    }
    "do nothing on invalid input" in {
      tui.processInputLine("-")
      controller.game should be (controller.game)
    }
    "have a help in string form" in {
      tui.help shouldBe a [String]
    }
  }
}
