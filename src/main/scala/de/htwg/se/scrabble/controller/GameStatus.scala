package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.model.Card
import de.htwg.se.scrabble.model.gameField.GameField

/*object GameStatus extends Enumeration{
  type GameStatus = Value
  val P1, P2, FIRST_CARD, INIT, VALID, INVALID, END_GAME = Value
    VALID-> "valid equation",
    INVALID-> "invalid equation",*/

/*
trait teststate
case class init() extends teststate
case class P1() extends teststate
case class P2() extends teststate

object testcontext {
  var state = P1State
  def handle(s: teststate) = {
    s match {
      case P1() => state = P1State
      case P2() => state = P2State
    }
    state
  }
  def init: Unit = println("")
  def P1State: Unit = ???
  def P2State: Unit = ???

}*/

object GameStatus {
  trait State {
    def calPoint(controller: Controller, currentSum: Int): Option[GameField]
    def setGrid(controller: Controller,row:String,col:String,value:String): Either[GameField, String]
    def gameToString(controller: Controller): String
  }

  case class Init() extends State {
    override def setGrid(controller: Controller, row: String, col: String, value: String): Either[GameField, String]
    = Right("can't set grid when in init phase")
    override def gameToString(controller: Controller): String = "write init or gf [size] to begin"
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
  }

  case class P2() extends State {
    override def setGrid(controller: Controller, row: String, col: String, value: String): Either[GameField, String] = {
      Left(setGridConcrete("B", controller.gameField, row.toInt-1, col.toInt-1, value))
    }
    override def gameToString(controller: Controller): String = controller.gameField.gameToString("B")
    override def calPoint(controller: Controller, currentSum: Int): Option[GameField] = {
      val game = controller.gameField
      controller.gameStatus = P1()
      Some(game.copy(playerList = game.changePlayerAttr("B",game.playerList("B").copy(point = game.playerList("B").point+currentSum))))
    }
  }

  def setGridConcrete(player: String, gameField: GameField, row: Int, col: Int, value: String): GameField =
    gameField.copy(grid = gameField.grid.set(row, col, value), playerList = gameField.changePlayerAttr(player, gameField.playerList(player).useCard(Card(value))))
}
