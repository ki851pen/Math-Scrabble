package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.util.{Command, Memento}

class SetCommand(row: Int, col: Int, value: String, controller: Controller) extends Command {
  var pastStates: List[Memento] = Nil
  var futureStates: List[Memento] = Nil
  override def doStep: Unit = {
    pastStates = controller.createMemento() :: pastStates
    futureStates = Nil
    val either = controller.gameStatus.setGrid(controller, row, col, value)
    either match {
      case Left(gameField) =>
        controller.setGameField(gameField);
        controller.addToSum(controller.cell(row - 1, col-1).getPoint)
      case Right(someString) => println(someString)
    }
  }

  override def undoStep: Unit = {
    pastStates match {
      case Nil =>
      case head :: stack =>
        futureStates = controller.createMemento() :: futureStates
        controller.restoreFromMemento(head)
        pastStates = stack
    }
  }

  override def redoStep: Unit = {
    futureStates match {
      case Nil =>
      case head :: stack =>
        pastStates = head :: pastStates
        controller.restoreFromMemento(head)
        futureStates = stack
    }
  }
}
