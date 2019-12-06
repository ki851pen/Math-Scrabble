package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.model.Card
import de.htwg.se.scrabble.model.cell.Cell
import de.htwg.se.scrabble.model.gameField.GameField

object GameStatus {

  trait State {
    def setGrid(controller: Controller, row: Int, col: Int, value: String): Either[GameField, String]

    def calPoint(controller: Controller, currentSum: Int): Option[GameField]

    def gameToString(controller: Controller): String

  }

  def apply(player: String) = {
    if(player == "A" || player == "B")
      P(player)
    else println("Please user either P(\"A\") or P(\"B\")" )
  }
  case class Init() extends State {
    override def setGrid(controller: Controller, row: Int, col: Int, value: String): Either[GameField, String]
    = Right("can't set grid when in init phase")

    override def calPoint(controller: Controller, currentSum: Int): Option[GameField] = None

    override def gameToString(controller: Controller): String = "write init or gf [size] to begin"
  }

  case class firstCard() extends State {
    override def setGrid(controller: Controller, row: Int, col: Int, value: String): Either[GameField, String] = {
      val gameField = controller.getGameField
      val gridMiddle: Int = controller.gridSize / 2 + 1
      (row.toInt, col.toInt) match {
        case (a: Int, b: Int) if a == b && a == gridMiddle =>
          if (gameField.playerList("A").hand.contains(Card(value))) {
            controller.gameStatus = P("A")
            Left(setGridConcrete("A", controller.getGameField, row.toInt - 1, col.toInt - 1, value))
          } else {
            Right("Can only set card from hand")
          }
        case _ => Right("Your first move have to be in the middle of the grid")
      }
    }

    override def calPoint(controller: Controller, currentSum: Int): Option[GameField] = {
      println("To end turn please set equation first. If you can't do it write clr to clear hand and fh to fill hand and try again")
      None
    }

    override def gameToString(controller: Controller): String = controller.getGameField().gameToString("A")
  }

  case class P(player: String) extends State {
    private var newCellsOfTurn: List[(Int, Int)] = Nil
    override def setGrid(controller: Controller, row: Int, col: Int, value: String): Either[GameField, String] = {
      var intRow = row.toInt
      val intCol = col.toInt
      if (controller.cell(intRow, intCol).isSet) {
        Right("can't set already set cell")
      } else {
        newCellsOfTurn = (intRow, intCol) :: newCellsOfTurn
        Left(setGridConcrete(player, controller.getGameField(), row.toInt - 1, col.toInt - 1, value))
      }
    }

    override def calPoint(controller: Controller, currentSum: Int): Option[GameField] = {
      val game = controller.getGameField()
      controller.gameStatus = P(changePlayer(player))
      Some(game.copy(playerList = game.changePlayerAttr(player, game.playerList(player).copy(point = game.playerList(player).point + currentSum))))
    }

    override def gameToString(controller: Controller): String = controller.getGameField().gameToString(player)

    def getNewCells = newCellsOfTurn
  }

  def changePlayer(currentPlayer: String) = if (currentPlayer == "A") "B" else  "A"

  def setGridConcrete(player: String, gameField: GameField, row: Int, col: Int, value: String): GameField =
    gameField.copy(grid = gameField.grid.set(row, col, value), playerList = gameField.changePlayerAttr(player, gameField.playerList(player).useCard(Card(value))))
}
