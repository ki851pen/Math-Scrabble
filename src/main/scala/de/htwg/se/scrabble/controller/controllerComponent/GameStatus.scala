package de.htwg.se.scrabble.controller.controllerComponent

import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface

import scala.util.{Failure, Success, Try}

object GameStatus {

  trait State {
    def setGrid(controller: ControllerInterface, row: Int, col: Int, index: Int): Try[GameFieldInterface]

    def calPoint(controller: ControllerInterface, currentSum: Int): Option[GameFieldInterface]

    def gameToString(controller: ControllerInterface): String

  }

  case class Init() extends State {
    override def setGrid(controller: ControllerInterface, row: Int, col: Int, index: Int): Try[GameFieldInterface]
    = Failure(new IllegalStateException("can't set grid when in init state"))

    override def calPoint(controller: ControllerInterface, currentSum: Int): Option[GameFieldInterface] = None

    override def gameToString(controller: ControllerInterface): String = "write init or gf [size] to begin"

    override def toString: String = "init"
  }

  case class FirstCard() extends State {
    override def setGrid(controller: ControllerInterface, row: Int, col: Int, index: Int): Try[GameFieldInterface] = {
      val gameField = controller.getGameField
      val gridMiddle: Int = controller.gridSize / 2
      (row, col) match {
        case (a: Int, b: Int) if a == b && a == gridMiddle =>
          if (index >= 0 && index < gameField.playerList("A").hand.size) {
            controller.changeGamestatus(P("A"))
            Success(setGridConcrete("A", gameField, row, col, index))
          } else {
            Failure(new IllegalArgumentException("Can only set card from hand"))
          }
        case _ => Failure(new IllegalArgumentException("Your first move have to be in the middle of the grid"))
      }
    }

    override def calPoint(controller: ControllerInterface, currentSum: Int): Option[GameFieldInterface] = {
      println("To end turn please set equation first. If you can't do it write clr to clear hand and fh to fill hand and try again")
      None
    }

    override def gameToString(controller: ControllerInterface): String = controller.getGameField.gameToString("A")

    override def toString: String = "fc"
  }

  case class P(player: String) extends State {
    private var newCellsOfTurn: List[(Int, Int)] = Nil //!IMPORTANT fehlt noch cell from firstcard
    override def setGrid(controller: ControllerInterface, row: Int, col: Int, index: Int): Try[GameFieldInterface] = {
      if (controller.cell(row, col).isSet) {
        Failure(new Exception("can't set already set cell"))
      } else {
        if (controller.getGameField.grid.getNeighborsOf(row, col).values.forall(!_.isSet)) {
          Failure(new Exception("you have to set near already set Cell"))
        } else {
          newCellsOfTurn = (row , col) :: newCellsOfTurn
          Success(setGridConcrete(player, controller.getGameField, row, col, index))
        }
      }
    }

    override def calPoint(controller: ControllerInterface, currentSum: Int): Option[GameFieldInterface] = {
      val game = controller.getGameField
      controller.changeGamestatus(P(changePlayer(player)))
      Some(game.calPointForPlayer(player, currentSum))
    }

    override def gameToString(controller: ControllerInterface): String = controller.getGameField.gameToString(player)

    def getNewCells: List[(Int, Int)] = newCellsOfTurn

    override def toString: String = "p"+player
  }

  def changePlayer(currentPlayer: String): String = if (currentPlayer == "A") "B" else  "A"

  def setGridConcrete(player: String, gameField: GameFieldInterface, row: Int, col: Int, index: Int): GameFieldInterface = {
    val card = gameField.playerList(player).hand(index)
    gameField.playerPlay(player, row, col, index)
  }
}
