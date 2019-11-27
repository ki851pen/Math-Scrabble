package de.htwg.se.scrabble.controller
import de.htwg.se.scrabble.model.cell.Cell
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
        observer.reset()
        controller.createEmptyGrid(4)
        observer.updated should be(true)
        controller.game.grid.size should be(4)
      }
      "notify its Observer after setting a first cell in middle" in {
        observer.reset()
        controller.createEmptyGrid(5)
        observer.reset()
        controller.createPile(1, 0, 0, 0, 0)
        controller.takeFromPile("A", 1)
        controller.setGrid("A","3","3","=")
        observer.updated should be(true)
        controller.game.grid.cell(2,2) shouldEqual Cell("=") //in input is already + 1
      }
      "not notify its Observer after setting a first cell not in middle" in {
        observer.reset()
        controller.createEmptyGrid(5)
        observer.reset()
        controller.setGrid("A","1","1","4")
        observer.updated should be(false)//<-- not pass
        controller.game.grid.cell(1,1) shouldEqual Cell("")
      }
      "notify its Observer after pile creation" in {
        observer.reset()
        controller.createPile(5, 1, 1, 1, 1)
        observer.updated should be(true)
        controller.game.pile.size should be(20)
      }
      "notify its Observer after shuffle the pile" in {
        observer.reset()
        val oldgame = controller.game
        controller.shufflePile()
        observer.updated should be(true)
        controller.game.pile should not be oldgame.pile
      }
      "notify its Observer after existing player take cards from pile" in {
        observer.reset()
        val oldgame = controller.game
        controller.takeFromPile("A", 3)
        observer.updated should be(true)
        controller.game.playerList("A").getNrCardsInHand should be (3)
        controller.game.pile.size should be (oldgame.pile.size -3)
      }
      "not notify its Observer after player that not in the list try take cards from pile" in {
        observer.reset()
        controller.takeFromPile("C", 3)
        observer.updated should be(false)
      }
      "notify its Observer after addition of new player" in {
        observer.reset()
        controller.addPlayer("B")
        observer.updated should be(true)
        controller.game.playerList.keys should contain ("B")
        controller.game.playerList.size should be (2)
      }
      "notify its Observer after removal of old player" in {
        observer.reset()
        controller.removePlayer("A")
        observer.updated should be(true)
        controller.game.playerList.keys should not contain "A"
        controller.game.playerList.size should be (1)
      }
      "notify its Observer after fill all player hands" in {
        observer.reset()
        controller.fillAllHand()
        observer.updated should be(true)
        controller.game.playerList("B").getNrCardsInHand should be (9)
      }
    }
  }
}



