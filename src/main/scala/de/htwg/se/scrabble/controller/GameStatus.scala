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
}*/

/*gameStatus match {
     ase FIRST_CARD if !(row == col && gameField.grid.size / 2 + 1 == row.toInt) => println("First Cell to set have to be in Middle of the Grid")
     //case FIRST_CARD if player == "B" => gameStatus = P2
     case P1 if player != "A" => println("It's A's turn")
     case P2 if player != "B" => println("It's B's turn")
     case P1|P2 if !gameField.playerList(player).hand.contains(Card(value)) => println("Can only set card from hand")
     case P1|P2 if gameField.grid.cell(row.toInt-1, col.toInt-1).isSet => println("can't set already set cell")
     case P1|P2 =>
       //gameField = gameField.copy(grid = gameField.grid.set(row.toInt-1, col.toInt-1, value), playerList = gameField.changePlayerAttr(player,gameField.playerList(player).useCard(Card(value))))
       currentSum += gameField.grid.cell(row.toInt-1, col.toInt-1).getPoint
       println(currentSum)
       notifyObservers
     case _ => println("cannot set grid if not in player turn")
    }*/

object GameStatus {

  trait State {
    def init(controller: Controller): Unit
    def calPoint(controller: Controller, currentSum: Int): Option[GameField]
    def setGrid(controller: Controller,row:String,col:String,value:String): Option[GameField]
    def gameToString(controller: Controller): String
  }

  case class Init() extends State {
    override def setGrid(controller: Controller, row: String, col: String, value: String): Option[GameField]
    = None

    override def gameToString(controller: Controller): String = "write init or gf [size] to begin"

    override def init(controller: Controller): Unit = {
      println("------ Start of Initialisation ------")
      controller.createFixedSizeGameField(15)
      controller.fillAllHand()
    }

    override def calPoint(controller: Controller, currentSum: Int): Option[GameField] = ???
  }

  case class firstCard() extends State {
    override def setGrid(controller: Controller, row: String, col: String, value: String): Option[GameField] = {
      val gameField = controller.getGameField
      val gridMiddle: Int = gameField.grid.size / 2 + 1
      (row.toInt, col.toInt) match {
        case (a:Int,b:Int) if a == b && a == gridMiddle => {
          controller.gameStatus = P1()
          Some(setGridConcrete(controller.getGameField,row.toInt-1,col.toInt-1,value))
        }
        case _ => None
      }
    }
    override def gameToString(controller: Controller): String = controller.getGameField.gameToStringWOPlayer

    override def init(controller: Controller): Unit = println("can't init again")

    override def calPoint(controller: Controller, currentSum: Int): Option[GameField] = ???
  }

  case class P1() extends State {
    override def setGrid(controller: Controller, row: String, col: String, value: String): Option[GameField] = {
      Some(setGridConcrete(controller.getGameField,row.toInt-1,col.toInt-1,value))
    }
    override def gameToString(controller: Controller): String = controller.getGameField.gameToString("A")

    override def calPoint(controller: Controller, currentSum: Int): Option[GameField] = {
      val game = controller.getGameField
      controller.gameStatus = P2()
      controller.fillHand("A")
      Some(game.copy(playerList = game.changePlayerAttr("A",game.playerList("A").copy(point = game.playerList("A").point+currentSum))))
    }

    override def init(controller: Controller): Unit = println("can't init again")
  }

  case class P2() extends State {
    override def setGrid(controller: Controller, row: String, col: String, value: String): Option[GameField] = {
      Some(setGridConcrete(controller.getGameField,row.toInt-1,col.toInt-1,value))
    }
    override def gameToString(controller: Controller): String = controller.getGameField.gameToString("B")

    override def init(controller: Controller): Unit = println("can't init again")

    override def calPoint(controller: Controller, currentSum: Int): Option[GameField] = ???
  }

  def setGridConcrete(gameField: GameField, row: Int, col: Int, value: String): GameField =
    gameField.copy(grid = gameField.grid.set(row, col, value), playerList = gameField.changePlayerAttr("A", gameField.playerList("A").useCard(Card(value))))
}
