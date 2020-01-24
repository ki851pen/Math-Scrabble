package de.htwg.se.scrabble.controller.controllerComponent.controllerBaseImpl


import com.google.inject.Inject
import de.htwg.se.scrabble.controller.controllerComponent.GameStatus._
import de.htwg.se.scrabble.controller.controllerComponent.{ControllerInterface, _}
import de.htwg.se.scrabble.model.fileIoComponent.FileIOInterface
import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl._
import de.htwg.se.scrabble.model.gridComponent.{CardInterface, CellInterface}
import de.htwg.se.scrabble.util.{Memento, UndoManager}

import scala.swing.Publisher

class Controller @Inject()(var gameFieldCreateStrategy: GameFieldCreateStrategyTemplate, fileIO: FileIOInterface) extends ControllerInterface with Publisher{
  private var gameField: GameFieldInterface = gameFieldCreateStrategy.createNewGameField
  private var gameState: State = Init()
  private val undoManager = new UndoManager
  private var currentSum: Int = 0
  private var beginTurn: Memento = createMemento()

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

  def createMemento(): Memento = Memento(gameField, gameState, currentSum)

  def restoreFromMemento(restore: Memento): Unit = {
    this.gameField = restore.gameField
    this.gameState = restore.gameStatus
    this.currentSum = restore.currentSum
    publish(new GameFieldChanged)
  }

  def setGrid(row: Int, col: Int, index: Int): Unit = {
    undoManager.doStep(new SetCommand(row, col, index, this))
    publish(ButtonSet(row, col))
  }

  def save: Unit = {
    fileIO.save(createMemento())
    publish(new GameFieldChanged)
    println("saved")
  }

  def load: Unit = {
    restoreFromMemento(fileIO.load)
    publish(new GameFieldChanged)
    println("loaded")
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
    beginTurn = createMemento()
  }
  
  def merge_numbers(CellsHorizontalA: ArrayBuffer[String]): ArrayBuffer[String] = {
    // 1. Nebeneinander stehende Zahlen miteinander verschmelzen, damit keine Konflikte bei +/-/*//
    var index_last = CellsHorizontalA.length - 1
    for (u <- CellsHorizontalA) {
      breakable {
        u match {
          case "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" => val index = CellsHorizontalA.indexOf(u)
            if (index != index_last) {
              if (CellsHorizontalA(index + 1).matches("\\d")) {
                val new_number = CellsHorizontalA(index) + CellsHorizontalA(index + 1)
                CellsHorizontalA(index) = new_number
                CellsHorizontalA -= CellsHorizontalA(index + 1)
                index_last -= 1
              }
            }
          case default => break()
        }
      }
    }
    return CellsHorizontalA
  }

  def mult_and_div(CellsHorizontalA: ArrayBuffer[String]): ArrayBuffer[String] = {
    // 2.  Liste nach * und /  durchschauen
    var counter = 0
    if((CellsHorizontalA.contains("*") || CellsHorizontalA.contains("/")) && CellsHorizontalA.length >= 3) {
      breakable {
        for (c <- CellsHorizontalA) {
          c match {
            case "*" => val index = CellsHorizontalA.indexOf(c)
              if (!CellsHorizontalA(index + 1).matches("\\d")) {
                return CellsHorizontalA.empty
              }
              val new_number = CellsHorizontalA(index - 1).toInt * CellsHorizontalA(index + 1).toInt
              val new_number_string = new_number.toString
              CellsHorizontalA(index - 1) = new_number_string
              for (i <- ((index + 2) until CellsHorizontalA.length)) {
                val change = CellsHorizontalA(i)
                CellsHorizontalA(i - 2) = change
                counter += 1
              }
              while (counter != 0) {
                CellsHorizontalA -= CellsHorizontalA(index + 2)
                counter -= 1
              }


            case "/" => val index = CellsHorizontalA.indexOf(c)
              if (!CellsHorizontalA(index + 1).matches("\\d")) {
                return CellsHorizontalA.empty
              }
              if (CellsHorizontalA(index + 1) == "0") {
                return CellsHorizontalA.empty
              }
              val new_number = CellsHorizontalA(index - 1).toInt / CellsHorizontalA(index + 1).toInt
              val new_number_str = new_number.toString
              CellsHorizontalA(index - 1) = new_number_str

              for (i <- ((index + 2) until CellsHorizontalA.length)) {
                val change_div = CellsHorizontalA(i)
                CellsHorizontalA(i - 2) = change_div
                counter += 1
              }
              while (counter != 0) {
                CellsHorizontalA -= CellsHorizontalA(index + 2)
                counter -= 1
              }

            case null => break()

            case default => if (c.matches("\\d{1,2}")) {
              val index = CellsHorizontalA.indexOf(c)
              CellsHorizontalA(index) = c
            }
          }
        }
      }
    }
    return CellsHorizontalA
  }

  def plus_and_min(CellsHorizontalA: ArrayBuffer[String]): ArrayBuffer[String] = {
    //3. jetzt + und minus verrechnen
    var counter = 0
    if((CellsHorizontalA.contains("+") || CellsHorizontalA.contains("-")) && CellsHorizontalA.length >= 3) {
      breakable {
        for (t <- CellsHorizontalA) {
          t match {
            case "+" => val i = CellsHorizontalA.indexOf(t)
              if (!CellsHorizontalA(i + 1).matches("\\d")) {
                return CellsHorizontalA.empty
              }
              val new_number = CellsHorizontalA(i - 1).toInt + CellsHorizontalA(i + 1).toInt
              val new_number_string = new_number.toString
              CellsHorizontalA(i - 1) = new_number_string
              for (i <- ((i + 2) until CellsHorizontalA.length)) {
                val change = CellsHorizontalA(i)
                CellsHorizontalA(i - 2) = change
                counter += 1
              }
              while (counter != 0) {
                CellsHorizontalA -= CellsHorizontalA(i + 2)
                counter -= 1
              }

            case "-" => val i = CellsHorizontalA.indexOf(t)
              if (!CellsHorizontalA(i + 1).matches("\\d")) {
                return CellsHorizontalA.empty
              }
              val new_number = CellsHorizontalA(i - 1).toInt - CellsHorizontalA(i + 1).toInt
              val new_number_string = new_number.toString
              CellsHorizontalA(i - 1) = new_number_string
              for (i <- ((i + 2) until CellsHorizontalA.length)) {
                val change = CellsHorizontalA(i)
                CellsHorizontalA(i - 2) = change
                counter += 1
              }
              while (counter != 0) {
                CellsHorizontalA -= CellsHorizontalA(i + 2)
                counter -= 1
              }

            case null => break()

            case default => if (t.matches("\\d{1,2}")) {
              val index = CellsHorizontalA.indexOf(t)
              CellsHorizontalA(index) = t
            }
          }
        }
      }
    }
    return CellsHorizontalA
  }


  def checkEquation(): Boolean = {
  var CellsHorizontalLeft: List[String] = Nil
    var CellsHorizontalRight: List[String] = Nil
    var CellsVerticalTop: List[String] = Nil
    var CellsVerticalBottom: List[String] = Nil
    val newCells = gameStatus.asInstanceOf[P].getNewCells
    if (newCells.isEmpty) {
      return false
    }
    for (cell <- newCells) {
      breakable {
        var origin_col_left = cell._2 - 1
        var origin_col_right = cell._2 + 1
        val origin_cell = this.cell(cell._1, cell._2)
        val neighbors = gameField.grid.getNeighborsOf(cell._1, cell._2)
        for (neighbor <- neighbors) {
          if (this.cell(cell._1, cell._2).card.isOperator && neighbor._2.card.isOperator) return false
        }

        // Problem mit toString.toInt wenn nicht Zahl sondern Operator
        while (this.cell(cell._1, origin_col_left).isSet) {
          val value_l = this.cell(cell._1, origin_col_left).card.toString
          CellsHorizontalLeft = CellsHorizontalLeft :+ value_l
          origin_col_left -= 1

        }
        val reverse_h_left = CellsHorizontalLeft.reverse // alle Zellen links von origin_cell
        val value_ori = origin_cell.card.toString
        CellsHorizontalRight = CellsHorizontalRight :+ value_ori // original Zelle hinzufügen

        while (this.cell(cell._1, origin_col_right).isSet) {
          val value_r = this.cell(cell._1, origin_col_right).card.toString
          CellsHorizontalRight = CellsHorizontalRight :+ value_r
          origin_col_right += 1
        }
        var CellsHorizontal = reverse_h_left ::: CellsHorizontalRight
        var CellsHorizontalA = new ArrayBuffer[String]()

        for (e <- CellsHorizontal) {
          CellsHorizontalA += e
        }

        if (CellsHorizontalA.length == 1) {
          CellsHorizontalRight = Nil
          CellsHorizontalLeft = Nil
          for (i <- CellsHorizontalA) {
            CellsHorizontalA -= CellsHorizontalA(CellsHorizontalA.indexOf(i))
          }
          break()
        }

        CellsHorizontalA = merge_numbers(CellsHorizontalA)
        CellsHorizontalA = mult_and_div(CellsHorizontalA)
        if (CellsHorizontalA.isEmpty) {
          return false
        }
        CellsHorizontalA = plus_and_min(CellsHorizontalA)
        if (CellsHorizontalA.isEmpty) {
          return false
        }

        //4. Überprüfen, ob Gleichung richtig ist
        if (!(CellsHorizontalA.contains("=") && CellsHorizontalA.length == 3)) {
          CellsHorizontalRight = Nil
          CellsHorizontalLeft = Nil
          CellsHorizontal = Nil
          CellsHorizontalA = CellsHorizontalA.empty
          return false
        }
        var s = ""
        for (e <- CellsHorizontalA) {
          s += e
        }
        breakable {
          s match {
            case "?[+-*/=].*?[+-*/=]" => return false
            case default => break()
          }
        }
        if (CellsHorizontalA.size != 3) {
          for (i <- (2 until CellsHorizontalA.size - 2)) {
            if ((CellsHorizontalA(0).toInt == CellsHorizontalA(CellsHorizontalA.size).toInt) == CellsHorizontalA(i)) {
              return true
            }
            else {
              return false
            }
          }
        }
        else {
          if (CellsHorizontalA(0).toInt == CellsHorizontalA(CellsHorizontalA.size - 1).toInt) {
            return true
          }
          else {
            return false
          }
        }
      }

        for (cell <- newCells) {
          breakable {
            var origin_row_top = cell._1 - 1 // left
            var origin_row_bottom = cell._1 + 1
            val origin_cell = this.cell(cell._1, cell._2)
            val neighbors = gameField.grid.getNeighborsOf(cell._1, cell._2)
            for (neighbor <- neighbors) {
              if (this.cell(cell._1, cell._2).card.isOperator && neighbor._2.card.isOperator) return false
            }

            // Problem mit toString.toInt wenn nicht Zahl sondern Operator
            while (this.cell(origin_row_top, cell._2).isSet) {
              val value_l = this.cell(origin_row_top, cell._2).card.toString
              CellsVerticalTop = CellsVerticalTop :+ value_l
              origin_row_top -= 1

            }
            val reverse_h_left = CellsVerticalTop.reverse // alle Zellen links von origin_cell
            val value_ori = origin_cell.card.toString
            CellsVerticalBottom = CellsVerticalBottom :+ value_ori // original Zelle hinzufügen

            while (this.cell(origin_row_bottom, cell._2).isSet) {
              val value_r = this.cell(origin_row_bottom, cell._2).card.toString
              CellsVerticalBottom = CellsVerticalBottom :+ value_r
              origin_row_bottom += 1
            }
            var CellsVertical = reverse_h_left ::: CellsVerticalBottom
            var CellsVerticalA = new ArrayBuffer[String]()

            for (e <- CellsVertical) {
              CellsVerticalA += e
            }

            CellsVerticalA = merge_numbers(CellsVerticalA)
            CellsVerticalA = mult_and_div(CellsVerticalA)
            if (CellsVerticalA.isEmpty) {
              return false
            }
            CellsVerticalA = plus_and_min(CellsVerticalA)
            if (CellsVerticalA.isEmpty) {
              return false
            }

            //4. Überprüfen, ob Gleichung richtig ist
            if (!(CellsVerticalA.contains("=") && CellsVerticalA.length == 3)) {
              CellsVerticalBottom = Nil
              CellsVerticalTop = Nil
              CellsVertical = Nil
              CellsVerticalA = CellsVerticalA.empty
              return false
            }
            var s = ""
            for (e <- CellsVerticalA) {
              s += e
            }
            breakable {
              s match {
                case "?[+-*/=].*?[+-*/=]" => return false
                case default => break()
              }
            }
            if (CellsVerticalA.size != 3) {
              for (i <- (2 until CellsVerticalA.size - 2)) {
                if ((CellsVerticalA(0).toInt == CellsVerticalA(CellsVerticalA.size).toInt) == CellsVerticalA(i)) {
                  return true
                }
                else {
                  return false
                }
              }
            }
            else {
              if (CellsVerticalA(0).toInt == CellsVerticalA(CellsVerticalA.size - 1).toInt) {
                return true
              }
              else {
                return false
              }
            }
        }
      }
    }
    return true
  }

  private def takeCardsBack(): Unit = {
    restoreFromMemento(beginTurn)
    publish(new GameFieldChanged)
    publish(new InvalidEquation)
  }

  def endTurn: Unit = {
    //if submit after first card always pass checkEquation: should NOT be like this
    if (!checkEquation()) {
      takeCardsBack()
    } else {
      gameField = gameState.calPoint(this, currentSum).getOrElse(gameField)
      currentSum = 0
      fillAllHand
      undoManager.resetStack()
      publish(new GameFieldChanged)
      beginTurn = createMemento()
    }
  }

  def createFixedSizeGameField(fixedSize: Int): Unit = {
    val oldsize = gameField.grid.size
    gameFieldCreateStrategy = new GameFieldFixedSizeCreateStrategy(fixedSize)
    gameField = gameFieldCreateStrategy.createNewGameField
    gameState = FirstCard()
    if (gameField.grid.size == oldsize) publish(new GameFieldChanged) else publish(new GridSizeChanged)
  }

  def createFreeSizeGameField(sizeGrid: Int, equal: Int, plusminus: Int, muldiv: Int, digit: Int): Unit = {
    gameFieldCreateStrategy = new GameFieldFreeSizeCreateStrategy(sizeGrid, equal, plusminus, muldiv, digit)
    gameField = gameFieldCreateStrategy.createNewGameField
    gameState = FirstCard()
    publish(new GridSizeChanged)
  }

  def createPile(equal: Int, plusminus: Int, muldiv: Int, digit: Int): Unit = {
    gameField = gameField.createNewPile(equal, plusminus, muldiv, digit)
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
