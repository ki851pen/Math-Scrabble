package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.model.Card
import de.htwg.se.scrabble.model.gameField.GameField

/*object GameStatus extends Enumeration{
  type GameStatus = Value
  val P1, P2, FIRST_CARD, INIT, VALID, INVALID, END_GAME = Value

  val map: Map[GameStatus, String] = Map[GameStatus, String](
    P1 -> "A's turn",
    P2 -> "B's turn",
    INIT -> "Initialisation",
    FIRST_CARD -> "first card",
    /*VALID-> "valid equation",
    INVALID-> "invalid equation",*/
    END_GAME ->"Game ended"
  )

  def message(gameStatus: GameStatus): String = {
    map(gameStatus)
  }
}*/

object GameStatus {
  trait State {
    def setGrid(controller: Controller,row:String,col:String,value:String): Option[GameField]
    def gameToString(controller: Controller): String
  }
  case class firstCard() extends State {
    override def setGrid(controller: Controller, row: String, col: String, value: String): Option[GameField] = {
      val gameField = controller.getGameField
      val gridMiddle: Int = gameField.grid.size / 2 + 1
      (row.toInt, col.toInt) match {
        case (a:Int,b:Int) if a == b && a == gridMiddle => {
          controller.gameStatus = P1
          Some(setGridConcrete(controller.getGameField,row.toInt-1,col.toInt-1,value))
        }
        case _ => None
      }
    }
    override def gameToString(controller: Controller): String = ???
  }
  case class P1() extends State {
    override def setGrid(controller: Controller, row: String, col: String, value: String): Option[GameField] = {
      Some(setGridConcrete(controller.getGameField,row.toInt-1,col.toInt-1,value))
    }
    override def gameToString(controller: Controller): String = controller.getGameField.gameToString("A")
  }
  case class P2() extends State {
    override def setGrid(controller: Controller, row: String, col: String, value: String): Option[GameField] = {
      Some(setGridConcrete(controller.getGameField,row.toInt-1,col.toInt-1,value))
    }
    override def gameToString(controller: Controller): String = controller.getGameField.gameToString("B")
  }
  case class Init() extends State {
    override def setGrid(controller: Controller, row: String, col: String, value: String): Option[GameField] = {
      None
    }
    override def gameToString(controller: Controller): String = ???
  }

  def setGridConcrete(gameField: GameField, row: Int, col: Int, value: String): GameField =
    gameField.copy(grid = gameField.grid.set(row, col, value), playerList = gameField.changePlayerAttr("A", gameField.playerList("A").useCard(Card(value))))
}
