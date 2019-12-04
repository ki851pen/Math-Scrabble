package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.model.Card
import de.htwg.se.scrabble.model.gameField.GameField

/*object GameStatus extends Enumeration{
  type GameStatus = Value
  val P1, P2, FIRST_CARD, INIT, VALID, INVALID, END_GAME = Value
    VALID-> "valid equation",
    INVALID-> "invalid equation",*/

object GameStatus {

  trait State {
    def init(controller: Controller): Unit
    def calPoint(controller: Controller, currentSum: Int): Option[GameField]
    def setGrid(controller: Controller,row:String,col:String,value:String): Either[GameField, String]
    def gameToString(controller: Controller): String
  }

  case class Init() extends State {
    override def setGrid(controller: Controller, row: String, col: String, value: String): Either[GameField, String]
    = Right("can't set grid when in init phase")

    override def gameToString(controller: Controller): String = "write init or gf [size] to begin"

    override def init(controller: Controller): Unit = {
      println("------ Start of Initialisation ------")
      controller.createFixedSizeGameField(15)
      controller.fillAllHand()
    }

    override def calPoint(controller: Controller, currentSum: Int): Option[GameField] = None
  }


  case class firstCard() extends State {
    override def setGrid(controller: Controller, row: String, col: String, value: String): Either[GameField, String] = {
      val gameField = controller.gameField
      val gridMiddle: Int = controller.gridSize() / 2 + 1
      (row.toInt, col.toInt) match {
        case (a:Int,b:Int) if a == b && a == gridMiddle =>
          if (gameField.playerList("A").hand.contains(Card(value))) {
            controller.gameStatus = P1()
            Left(setGridConcrete("A",controller.gameField,row.toInt-1,col.toInt-1,value))
          } else {
            Right("Can only set card from hand")
          }
        case _ => Right("Your first move have to be in the middle of the grid")
      }
    }
    override def gameToString(controller: Controller): String = controller.gameField.gameToString("A")

    override def init(controller: Controller): Unit = println("can't init again")

    override def calPoint(controller: Controller, currentSum: Int): Option[GameField] = {
      println("To end turn please set equation first. If you can't do it write clr to clear hand and fh to fill hand and try again")
      None
    }
  }


  case class P1() extends State {
    override def setGrid(controller: Controller, row: String, col: String, value: String): Either[GameField, String] = {
      Left(setGridConcrete("A", controller.gameField, row.toInt-1, col.toInt-1, value))
    }
    override def gameToString(controller: Controller): String = controller.gameField.gameToString("A")

    override def calPoint(controller: Controller, currentSum: Int): Option[GameField] = {
      val game = controller.gameField
      controller.gameStatus = P2()
      Some(game.copy(playerList = game.changePlayerAttr("A",game.playerList("A").copy(point = game.playerList("A").point+currentSum))))
    }

    override def init(controller: Controller): Unit = println("can't init again")
  }


  case class P2() extends State {
    override def setGrid(controller: Controller, row: String, col: String, value: String): Either[GameField, String] = {
      Left(setGridConcrete("B", controller.gameField, row.toInt-1, col.toInt-1, value))
    }
    override def gameToString(controller: Controller): String = controller.gameField.gameToString("B")

    override def init(controller: Controller): Unit = println("can't init again")

    override def calPoint(controller: Controller, currentSum: Int): Option[GameField] = {
      val game = controller.gameField
      controller.gameStatus = P1()
      Some(game.copy(playerList = game.changePlayerAttr("B",game.playerList("B").copy(point = game.playerList("B").point+currentSum))))
    }
  }


  def setGridConcrete(player: String, gameField: GameField, row: Int, col: Int, value: String): GameField =
    gameField.copy(grid = gameField.grid.set(row, col, value), playerList = gameField.changePlayerAttr(player, gameField.playerList(player).useCard(Card(value))))
}
