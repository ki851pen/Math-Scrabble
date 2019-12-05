package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.util.{Command, Memento}

class SetCommand(row: String, col: String, value: String, controller: Controller) extends Command {
  var pastStates: List[Memento] = Nil
  var futureStates: List[Memento] = Nil
  override def doStep: Unit = {
    pastStates = controller.createMemento() :: pastStates
    futureStates = Nil
    val either = controller.gameStatus.setGrid(controller, row, col, value)
    either match {
      case Left(x) => controller.gameField = x; controller.addToSum(controller.cell(row.toInt-1, col.toInt-1).getPoint); controller.notifyObservers;
      case Right(x) => println(x)
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
    controller.notifyObservers
  }

  override def redoStep: Unit = {
    futureStates match {
      case Nil =>
      case head :: stack =>
        pastStates = head :: pastStates
        controller.restoreFromMemento(head)
        futureStates = stack
    }
    controller.notifyObservers
  }
}
