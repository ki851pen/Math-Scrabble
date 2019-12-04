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
      "notify its Observer after init" in {
        observer.reset()
        controller.init()
        observer.updated should be(true)
        controller.gameField.grid.size shouldBe 15
        controller.gameField.playerList("A").getNrCardsInHand should not be 0
        controller.gameField.playerList("B").getNrCardsInHand should not be 0
        controller.gameStatus = GameStatus.P1()
      }
      "notify its Observer after calculate point" in {
        observer.reset()
        controller.endTurn()
        observer.updated should be(true)
        controller.gameStatus = GameStatus.P2()
        observer.reset()
        controller.endTurn()
        observer.updated should be(true)
        controller.gameStatus = GameStatus.P1()
      }
      "notify its Observer after grid creation" in {
        observer.reset()
        controller.createFixedSizeGameField(3)
        observer.updated should be(true)
        controller.gameField.grid.size should be(3)
        controller.gameToString should be(controller.gameField.gameToString("A"))
      }
      "notify its Observer after grid creation with free size" in {
        observer.reset()
        controller.createFreeSizeGameField(4, 1, 1, 1, 1, 1)
        observer.updated should be(true)
        controller.gameField.grid.size should be(4)
      }
      "notify its Observer after setting a first cell in middle" in {
        observer.reset()
        controller.createFixedSizeGameField(5)
        observer.reset()
        controller.createPile(1, 0, 0, 0, 0)
        controller.fillHand("A")
        controller.setGrid("3","3","=")
        observer.updated should be(true)
        controller.gameField.grid.cell(2,2) shouldEqual Cell("=")//in input is already + 1
      }
      "not notify its Observer after setting a first cell not in middle" in {
        observer.reset()
        controller.createFixedSizeGameField(5)
        observer.reset()
        controller.setGrid("1","1","4")
        observer.updated should be(false)//<-- not pass
        controller.gameField.grid.cell(1,1) shouldEqual Cell("")
      }
      "notify its Observer after pile creation" in {
        observer.reset()
        controller.createPile(5, 1, 1, 1, 1)
        observer.updated should be(true)
        controller.gameField.pile.size should be(20)
      }
      "notify its Observer after shuffle the pile" in {
        observer.reset()
        val oldgame = controller.gameField
        controller.shufflePile()
        observer.updated should be(true)
        controller.gameField.pile should not be oldgame.pile
      }
      "notify its Observer after existing player take cards from pile" in {
        observer.reset()
        val oldgame = controller.gameField
        controller.fillHand("A")
        observer.updated should be(true)
        controller.gameField.playerList("A").getNrCardsInHand should be (9)
        controller.gameField.pile.size should be (oldgame.pile.size -9)
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
        controller.gameField.playerList.keys should contain ("B")
        controller.gameField.playerList.size should be (2)
      }
      "notify its Observer after removal of old player" in {
        observer.reset()
        controller.removePlayer("A")
        observer.updated should be(true)
        controller.gameField.playerList.keys should not contain "A"
        controller.gameField.playerList.size should be (1)
      }
      "notify its Observer after fill all player hands" in {
        observer.reset()
        controller.fillAllHand()
        observer.updated should be(true)
        controller.gameField.playerList("B").getNrCardsInHand should be (9)
      }
      "notify its Observer after clear a player hand" in {
        val oldPileSize: Int = controller.gameField.pile.size
        observer.reset()
        controller.clearHand("B")
        observer.updated should be(true)
        controller.gameField.playerList("B").getNrCardsInHand should be (0)
        controller.gameField.pile.size should be (oldPileSize + 9)
      }
    }
  }
}



