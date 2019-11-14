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
    "set a cell on input '8 8 3'" in {
      tui.processInputLine("8 8 3")
      controller.game.grid.cell(7,7).value should be("3")
    }
  }
}
