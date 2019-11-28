package de.htwg.se.scrabble.controller
import de.htwg.se.scrabble.model.cell.Cell
import de.htwg.se.scrabble.model.gameField.GameFieldFreeSizeCreateStrategy
import de.htwg.se.scrabble.util.Observer
import org.scalatest.{Matchers, WordSpec}

class ControllerSpec extends WordSpec with Matchers{
  "A Controller" when {
    "observed by an Observer" should {
      val gameFieldCreateStrategy = new GameFieldFreeSizeCreateStrategy(5, 1, 1, 1, 1,1)
      val controller = new Controller(gameFieldCreateStrategy)
      val observer = new Observer {
        var updated: Boolean = false
        def reset(): Unit = updated = false
        override def update: Boolean = {updated = true; updated}
      }
      controller.add(observer)
      "notify its Observer after grid creation" in {
        observer.reset()
        controller.createFixedSizeGameField(3)
        observer.updated should be(true)
        controller.getGameField.grid.size should be(3)
        controller.gameToString should be(controller.getGameField.toString)
      }
      "notify its Observer after grid creation with free size" in {
        observer.reset()
        controller.createFreeSizeGameField(4, 1, 1, 1, 1, 1)
        observer.updated should be(true)
        controller.getGameField.grid.size should be(4)
      }
      "notify its Observer after setting a first cell in middle" in {
        observer.reset()
        controller.createFixedSizeGameField(5)
        observer.reset()
        controller.createPile(1, 0, 0, 0, 0)
        controller.fillHand("A")
        controller.setGrid("A","3","3","=")
        observer.updated should be(true)
        controller.getGameField.grid.cell(2,2) shouldEqual Cell("=")//in input is already + 1
      }
      "not notify its Observer after setting a first cell not in middle" in {
        observer.reset()
        controller.createFixedSizeGameField(5)
        observer.reset()
        controller.setGrid("A","1","1","4")
        observer.updated should be(false)//<-- not pass
        controller.getGameField.grid.cell(1,1) shouldEqual Cell("")
      }
      "notify its Observer after pile creation" in {
        observer.reset()
        controller.createPile(5, 1, 1, 1, 1)
        observer.updated should be(true)
        controller.getGameField.pile.size should be(20)
      }
      "notify its Observer after shuffle the pile" in {
        observer.reset()
        val oldgame = controller.getGameField
        controller.shufflePile()
        observer.updated should be(true)
        controller.getGameField.pile should not be oldgame.pile
      }
      "notify its Observer after existing player take cards from pile" in {
        observer.reset()
        val oldgame = controller.getGameField
        controller.fillHand("A")
        observer.updated should be(true)
        controller.getGameField.playerList("A").getNrCardsInHand should be (9)
        controller.getGameField.pile.size should be (oldgame.pile.size -9)
      }
      "not notify its Observer after player that not in the list try take cards from pile" in {
        observer.reset()
        controller.fillHand("C")
        observer.updated should be(false)
      }
      "notify its Observer after addition of new player" in {
        observer.reset()
        controller.addPlayer("B")
        observer.updated should be(true)
        controller.getGameField.playerList.keys should contain ("B")
        controller.getGameField.playerList.size should be (2)
      }
      "notify its Observer after removal of old player" in {
        observer.reset()
        controller.removePlayer("A")
        observer.updated should be(true)
        controller.getGameField.playerList.keys should not contain "A"
        controller.getGameField.playerList.size should be (1)
      }
      "notify its Observer after fill all player hands" in {
        observer.reset()
        controller.fillAllHand()
        observer.updated should be(true)
        controller.getGameField.playerList("B").getNrCardsInHand should be (9)
      }
      "notify its Observer after clear a player hand" in {
        val oldPileSize: Int = controller.getGameField.pile.size
        observer.reset()
        controller.clearHand("B")
        observer.updated should be(true)
        controller.getGameField.playerList("B").getNrCardsInHand should be (0)
        controller.getGameField.pile.size should be (oldPileSize + 9)
      }
    }
  }
}



