package de.htwg.se.scrabble.controller

import com.google.inject.Guice
import de.htwg.se.scrabble.ScrabbleModule
import de.htwg.se.scrabble.controller.controllerComponent.GameStatus.{FirstCard, Init}
import de.htwg.se.scrabble.controller.controllerComponent._
import de.htwg.se.scrabble.model.fileIoComponent._
import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl.GameFieldFreeSizeCreateStrategy
import org.scalatest.{Matchers, WordSpec}

class ControllerSpec extends WordSpec with Matchers{
  "A Controller" when {
    val gameFieldCreateStrategy = new GameFieldFreeSizeCreateStrategy(5, 1, 1, 1,1)
    val injector = Guice.createInjector(new ScrabbleModule)
    val fileIO = injector.getInstance(classOf[FileIOInterface])
    val controller = new controllerBaseImpl.Controller(gameFieldCreateStrategy, fileIO)
    "created" should {
      "has Status Init" in {
        controller.gameStatus shouldBe a [Init]
        controller.gameStatus.toString should be("init")
        controller.gameToString should be("write init or gf [size] to begin")
      }
      "has the size assigned when created" in {
        controller.gridSize shouldBe 5
      }
      "has unset cells" in {
        controller.cell(0, 0).isSet shouldBe false

        controller.getCurrentSum shouldBe 0
        controller.getGameField shouldBe a [GameFieldInterface]
      }
      "should has a set cell after setting cell" in {
        controller.setGrid(0, 0, 0)
        controller.isSet(0, 0) shouldBe false
      }
      "should has another status after a turn" in {
        val state = FirstCard()
        controller.changeGamestatus(state)
        controller.gameStatus.toString should be("fc")
      }

      "can undo its previous action" in {
        val memmento = controller.createMemento()
        val oldgamefield = controller.getGameField
        controller.addToSum(5)
        controller.restoreFromMemento(memmento)
        oldgamefield shouldBe controller.getGameField
      }

      "can also redo" in {
        controller.setGrid(1, 1, 1)
        val cell = controller.cell(1, 1)
        controller.undo
        controller.redo
        controller.cell(1, 1) shouldBe cell
      }

      "can save the current game and load it" in {
        val gameField = controller.getGameField
        controller.setGameField(gameField)
        controller.save
        controller.init
        controller.load
        controller.getGameField shouldBe gameField
        var newFileIO = new fileIoXmlImpl.FileIO
        val newController = new controllerBaseImpl.Controller(gameFieldCreateStrategy, newFileIO)
        newController.save
        newController.load
      }

      "can end a turn when a player played his turn" in {
        val check = controller.checkEquation()
        if (check){
          controller.endTurn
          controller.getCurrentSum shouldBe 0
        }
        else
          ""
      }

      "can manipulate player hand" in {
        controller.init
        controller.changeHand("A")
        controller.getCardsInHand("A").length shouldBe 10
        controller.clearHand("A")
        controller.getCardsInHand("A").length shouldBe 0
      }

      "select different cells" in {
        controller.selectedCellChanged(6, 6)
        controller.currentSelectedCol shouldBe 5
        controller.currentSelectedRow shouldBe 5
      }

      "pass all tests of Mock Implementation" in {
        val mockController = new controllerMockImpl.Controller(null)
        mockController.init
        mockController.createFixedSizeGameField(1)
        mockController.createFreeSizeGameField(0, 0, 0, 0, 0)
        mockController.createPile(0, 0, 0, 0)
        mockController.shufflePile
        mockController.setGrid(0, 0, 0)
        mockController.endTurn
        mockController.undo
        mockController.redo
        mockController.fillHand(null)
        mockController.fillAllHand
        mockController.getCardsInHand("")
        mockController.clearHand("")
        mockController.gameStatus shouldBe a [Init]
        mockController.currentSelectedCol shouldBe 0
        mockController.currentSelectedRow shouldBe 0
        mockController.selectedCellChanged(0, 0)
        mockController.chooseCardInHand(0)
        mockController.changeHand("")
        mockController.putCardInCell
        mockController.gameToString shouldBe ""
        mockController.changeGamestatus(null)
        mockController.save
        mockController.load
        val gameField = mockController.getGameField
        mockController.gridSize shouldBe 1
        mockController.getCurrentSum shouldBe 0
        mockController.cell(0, 0) shouldBe gameField.grid.cell(0, 0)
        mockController.isSet(0, 0) shouldBe false
        new CardsChanged
        new ClickChanged
        new InvalidEquation
        ButtonSet(0, 0).col shouldBe 0
      }
      }
  }
}



