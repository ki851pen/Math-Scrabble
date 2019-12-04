package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.controller.GameStatus._
import de.htwg.se.scrabble.model.Card
import de.htwg.se.scrabble.util.Command

class SetCommand(row: String, col: String, value: String, controller: Controller) extends Command{
  override def doStep: Unit = {
    val either = controller.gameStatus.setGrid(controller, row, col, value)
    either match {
      case Left(x) => controller.gameField = x; controller.addToSum(controller.cell(row.toInt-1, col.toInt-1).getPoint); controller.notifyObservers;
      case Right(x) => println(x)
    }
  }

  override def undoStep: Unit = {
    controller.reduceSum(controller.cell(row.toInt-1, col.toInt-1).getPoint)
    controller.gameStatus match {
      case P1() => controller.gameField = controller.gameField.copy(grid = controller.gameField.grid.setEmpty(row.toInt-1, col.toInt-1),
        playerList = controller.gameField.changePlayerAttr("A", controller.gameField.playerList("A").addToHand(Card(value) :: Nil)))
      case P2() => controller.gameField = controller.gameField.copy(grid = controller.gameField.grid.setEmpty(row.toInt-1, col.toInt-1),
        playerList = controller.gameField.changePlayerAttr("B", controller.gameField.playerList("B").addToHand(Card(value) :: Nil)))
    }
    controller.notifyObservers
  }

  override def redoStep: Unit = {
    //controller.gameField.copy(grid = controller.gameField.grid.setEmpty(row.toInt-1, col.toInt-1))
    controller.addToSum(controller.cell(row.toInt-1, col.toInt-1).getPoint)
    controller.notifyObservers
  }
}
