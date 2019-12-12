package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.model.Card
import de.htwg.se.scrabble.model.gameField.GameField

import scala.util.{Failure, Success, Try}

object GameStatus {

  trait State {
    def setGrid(controller: Controller, row: Int, col: Int, value: String): Try[GameField]

    def calPoint(controller: Controller, currentSum: Int): Option[GameField]

    def gameToString(controller: Controller): String

  }
/*
  def apply(player: String) = {
    if(player == "A" || player == "B")
      P(player)
    else println("Please user either P(\"A\") or P(\"B\")" )
  }*/

  case class Init() extends State {
    override def setGrid(controller: Controller, row: Int, col: Int, value: String): Try[GameField]
    = Failure(new IllegalStateException("can't set grid when in init state"))

    override def calPoint(controller: Controller, currentSum: Int): Option[GameField] = None

    override def gameToString(controller: Controller): String = "write init or gf [size] to begin"
  }

  case class FirstCard() extends State {
    override def setGrid(controller: Controller, row: Int, col: Int, value: String): Try[GameField] = {
      val gameField = controller.getGameField
      val gridMiddle: Int = controller.gridSize / 2
      (row, col) match {
        case (a: Int, b: Int) if a == b && a == gridMiddle =>
          if (gameField.playerList("A").hand.contains(Card(value))) {
            controller.gameStatus = P("A")
            Success(setGridConcrete("A", controller.getGameField, row, col, value))
          } else {
            Failure(new IllegalArgumentException("Can only set card from hand"))
          }
        case _ => Failure(new IllegalArgumentException("Your first move have to be in the middle of the grid"))
      }
    }

    override def calPoint(controller: Controller, currentSum: Int): Option[GameField] = {
      println("To end turn please set equation first. If you can't do it write clr to clear hand and fh to fill hand and try again")
      None
    }

    override def gameToString(controller: Controller): String = controller.getGameField.gameToString("A")
  }

  case class P(player: String) extends State {
    private var newCellsOfTurn: List[(Int, Int)] = Nil //fehlt noch cell from firstcard
    override def setGrid(controller: Controller, row: Int, col: Int, value: String): Try[GameField] = {
      if (controller.cell(row, col).isSet) {
        Failure(new Exception("can't set already set cell"))
      } else {
        if (controller.getGameField.grid.getNeighborsOf(row, col).values.forall(!_.isSet)) {
          Failure(new Exception("you have to set near already set Cell"))
        } else {
          newCellsOfTurn = (row , col) :: newCellsOfTurn
          Success(setGridConcrete(player, controller.getGameField, row, col, value))
        }
      }
    }

    override def calPoint(controller: Controller, currentSum: Int): Option[GameField] = {
      val game = controller.getGameField
      controller.gameStatus = P(changePlayer(player))
      Some(game.copy(playerList = game.changePlayerAttr(player, game.playerList(player).copy(point = game.playerList(player).point + currentSum))))
    }

    override def gameToString(controller: Controller): String = controller.getGameField.gameToString(player)

    def getNewCells: List[(Int, Int)] = newCellsOfTurn
  }

  def changePlayer(currentPlayer: String): String = if (currentPlayer == "A") "B" else  "A"

  def setGridConcrete(player: String, gameField: GameField, row: Int, col: Int, value: String): GameField =
    gameField.copy(grid = gameField.grid.set(row, col, value), playerList = gameField.changePlayerAttr(player, gameField.playerList(player).useCard(Card(value))))
}
