package de.htwg.se.scrabble.controller.controllerComponent.controllerBaseImpl

import com.google.inject.name.Names
import com.google.inject.{Guice, Inject}
import de.htwg.se.scrabble.ScrabbleModule
import de.htwg.se.scrabble.controller.ProcessEquation
import de.htwg.se.scrabble.controller.controllerComponent.GameStatus._
import de.htwg.se.scrabble.controller.controllerComponent.{ControllerInterface, _}
import de.htwg.se.scrabble.model.fileIoComponent.FileIOInterface
import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl._
import de.htwg.se.scrabble.model.gridComponent.{CardInterface, CellInterface}
import de.htwg.se.scrabble.util.{Memento, UndoManager}
import net.codingwell.scalaguice.InjectorExtensions._
import scala.swing.Publisher

class Controller @Inject() extends ControllerInterface with Publisher{
  val injector = Guice.createInjector(new ScrabbleModule)
  private var gameFieldCreateStrategy: GameFieldCreateStrategyTemplate = injector.instance[GameFieldCreateStrategyTemplate](Names.named("DefaultStrategy"))
  private var gameField: GameFieldInterface = gameFieldCreateStrategy.createNewGameField
  private var gameState: State = Init()
  private val undoManager = new UndoManager
  private var currentSum: Int = 0
  val fileIo = injector.instance[FileIOInterface]

  def gameStatus: State = gameState
  def changeGamestatus(newState: State): Unit = gameState = newState
  def gridSize: Int = gameField.grid.size

  def cell(row: Int, col: Int): CellInterface = gameField.grid.cell(row, col)
  def getCurrentSum: Int = currentSum

  def isSet(row: Int, col: Int): Boolean = gameField.grid.cell(row, col).isSet

  def addToSum(point: Int): Unit = currentSum += point

  def getGameField: GameFieldInterface = gameField

  def setGameField(gameField: GameFieldInterface): Unit = {
    this.gameField = gameField
    publish(new GameFieldChanged)
  }

  def setstate(gameField: GameFieldInterface, gameStatus: State, currentSum: Int): Unit = {
    this.gameField = gameField
    this.gameState = gameStatus
    this.currentSum = currentSum
  }

  def createMemento(): Memento = Memento(gameField, gameState, currentSum)

  def restoreFromMemento(restore: Memento): Unit = {
    this.gameField = restore.gameField
    this.gameState = restore.gameStatus
    this.currentSum = restore.currentSum
  }

  def setGrid(row: Int, col: Int, index: Int): Unit = {
    undoManager.doStep(new SetCommand(row, col, index, this))
    publish(ButtonSet(row, col))
  }

  def undo: Unit = {
    undoManager.undoStep
    publish(new GameFieldChanged)
  }

  def redo: Unit = {
    undoManager.redoStep
    publish(new GameFieldChanged)
  }

  def gameToString: String = gameState.gameToString(this)

  def init: Unit = {
    println("------ Start of Initialisation ------")
    createFixedSizeGameField(15)
    fillAllHand
  }

  def checkEquation(): Boolean = {
    /*funktioniert noch nicht
    val newCells = gameState.asInstanceOf[P].getNewCells
    for (cell <- newCells) {
      breakable {
        if (this.cell(cell._1, cell._2).card.isQuestionMark) break
        var origin_col_left = cell._2 - 1
        var origin_col_right = cell._2 + 1
        val origin_cell = this.cell(cell._1, cell._2)
        val neighbors = gameField.grid.getNeighborsOf(cell._1, cell._2)
        for (neighbor <- neighbors) {
          if (this.cell(cell._1, cell._2).card.isOperator && neighbor._2.card.isOperator) return false
        }

        // Problem mit toString.toInt wenn nicht Zahl sondern Operator
        while(this.cell(cell._1, origin_col_left).isSet) {
          val value_l = this.cell(cell._1, origin_col_left).card.toString.toInt
          val cellsHorizontalLeft = CellsHorizontalLeft:+value_l
          origin_col_left -= 1

        }
        val reverse_h_left = CellsHorizontalLeft.reverse // alle Zellen links von origin_cell
        CellsHorizontalRight:+origin_cell.card.toString // original Zelle hinzufügen

        while(this.cell(cell._1, origin_col_right).isSet) {
          val value_r = this.cell(cell._1, origin_col_right).card.toString.toInt
          val cellsHorizontalRight = CellsHorizontalRight:+value_r
          origin_col_right += 1
        }

        // prüfen, ob = in Liste vorhanden, wenn nein, dann prüfen, ob zahl operator zahl  ist und wenn nein, dann falsch
        // wenn = in Liste vorhanden, dann prüfen, ob linker Teil gleich ist wie rechter Teil
        val CellsHorizontal = CellsHorizontalLeft ::: CellsHorizontalRight
        val head = CellsHorizontal.head
        if (!CellsHorizontal.contains('=')) {
          if (!head.isValidInt && !(CellsHorizontal.contains('+') || CellsHorizontal.contains('-') || CellsHorizontal.contains('*') || CellsHorizontal.contains('/')) && !CellsHorizontal.last.isValidInt) {
            return false
          }
        }
        else {
          if (head.isValidInt) {
            var equation_left = head
            var index = 0
            for (c <- CellsHorizontal.tail) {
              while (!c.equals('=')) {
                equation_left += c
                index += 1
              }
            }
            var equation_right = 0
            if (CellsHorizontal(index +2).isValidInt) {
             for (i <- CellsHorizontal.slice(index+2, CellsHorizontal.size-1)) {
               equation_right += i
             }
            }
            if (!equation_left.equals(equation_right)) {
              return false
            }
          }
        }
        }

      }*/
    true
  }

  private def takeCardsBack(): Unit = {
    val cardsToTakeBack = gameState.asInstanceOf[P].getNewCells
    val currentPlayer = "A"
    //gameField = gameField.copy(grid = gameField.grid.clearCells(cardsToTakeBack),
      //playerList = gameField.changePlayerAttr(currentPlayer, gameField.playerList(currentPlayer).addToHand(cardsToTakeBack.map(pos => cell(pos._1, pos._2).card))))
  //not done
  }

  def endTurn: Unit = {
    ProcessEquation(this)
    //if (!checkEquation()) takeCardsBack()
    gameField = gameState.calPoint(this, currentSum).getOrElse(gameField)
    currentSum = 0
    fillAllHand
    undoManager.resetStack()
    publish(new GameFieldChanged)
  }

  def createFixedSizeGameField(fixedSize: Int): Unit = {
    val oldsize = gameField.grid.size
    gameFieldCreateStrategy = new GameFieldFixedSizeCreateStrategy(fixedSize)
    gameField = gameFieldCreateStrategy.createNewGameField
    gameState = FirstCard()
    if (gameField.grid.size == oldsize) publish(new GameFieldChanged) else publish(new GridSizeChanged)
  }

  def createFreeSizeGameField(sizeGrid: Int, equal: Int, plusminus: Int, muldiv: Int, blank: Int, digit: Int): Unit = {
    gameFieldCreateStrategy = new GameFieldFreeSizeCreateStrategy(sizeGrid, equal, plusminus, muldiv, blank, digit)
    gameField = gameFieldCreateStrategy.createNewGameField
    gameState = FirstCard()
    publish(new GridSizeChanged)
  }

  def createPile(equal: Int, plusminus: Int, muldiv: Int, blank: Int, digit: Int): Unit = {
    gameField = gameField.createNewPile(equal, plusminus, muldiv, blank, digit)
    publish(new CardsChanged)
  }

  def shufflePile: Unit = {
    gameField = gameField.shufflePile
  }

  def fillHand(name: String): Unit = {
    if (gameField.playerList.contains(name)) {
      val player = gameField.playerList(name)
      val nrLeftToFill = player.maxHandSize - player.getNrCardsInHand
      shufflePile
      gameField = gameField.fillHand(name, nrLeftToFill)
      publish(new CardsChanged)
    } else {
      println("Player " + name + " doesn't exist")
    }
  }

  def fillAllHand: Unit = {
    val playerName: Iterable[String] = gameField.playerList.keys
    playerName.foreach(p => fillHand(p))
  }

  def getCardsInHand(name: String): List[CardInterface] = {
    val player = gameField.playerList(name)
    player.hand
  }

  def clearHand(name: String): Unit = {
    if (gameField.playerList.contains(name)) {
      gameField = gameField.clearHand(name)
      shufflePile
      publish(new CardsChanged)
    } else {
      println("Player " + name + " doesn't exist")
    }
  }
  private var currentChosenCardIndex = -1

  def chooseCardInHand(indexOfCard: Int): Unit = {
    currentChosenCardIndex = indexOfCard
  }

  def currentSelectedRow: Int = currentRowOfSelectedCell
  def currentSelectedCol: Int = currentColOfSelectedCell
  private var currentRowOfSelectedCell = -1
  private var currentColOfSelectedCell = -1

  def selectedCellChanged(row: Int, col: Int): Unit = {
    currentRowOfSelectedCell = row - 1
    currentColOfSelectedCell = col - 1
    publish(new ClickChanged)
  }

  def putCardInCell: Unit = {
    setGrid(currentRowOfSelectedCell, currentColOfSelectedCell, currentChosenCardIndex)
  }

  def changeHand(playerName: String): Unit = {
    clearHand(playerName)
    fillHand(playerName)
    publish(new CardsChanged)
  }
}