package de.htwg.se.scrabble.controller
import de.htwg.se.scrabble.model.{Gamefield, Grid, Pile}
import de.htwg.se.scrabble.util.Observer
import org.scalatest.{Matchers, WordSpec}

class ControllerSpec extends WordSpec with Matchers{
  "A Controller" when {
    "observed by an Observer" should {
      val game = new Gamefield(new Grid(5), new Pile(1,1,1,1,1))
      val controller = new Controller(game)
      val observer = new Observer {
        var updated: Boolean = false
        def reset(): Unit = updated = false
        override def update: Unit = updated = true
      }
      controller.add(observer)
      "notify its Observer after grid creation" in {
        controller.createEmptyGrid(4)
        observer.updated should be(true)
        controller.game.grid.size should be(4)
      }
      "notify its Observer after setting a first cell in middle" in {
        controller.createEmptyGrid(5)
        observer.reset()
        controller.setGrid("3","3","4")
        observer.updated should be(true)
        controller.game.grid.cell(2,2).value should be ("4") //in input is already + 1
      }
      "not notify its Observer after setting a first cell not in middle" in {
        controller.createEmptyGrid(5)
        observer.reset()
        controller.setGrid("1","1","4")
        observer.updated should be(false)//<-- not pass
        controller.game.grid.cell(1,1).value should be ("")
      }
      "notify its Observer after pile creation" in {
        controller.createPile(5, 1, 1, 1, 1)
        observer.updated should be(true)
        controller.game.pile.size should be(20)
      }
      "notify its Observer after shuffle the pile" in {
        val oldgame = game
        controller.shufflePile()
        observer.updated should be(true)
        controller.game.pile should not be oldgame.pile
      }
    }
  }
}



