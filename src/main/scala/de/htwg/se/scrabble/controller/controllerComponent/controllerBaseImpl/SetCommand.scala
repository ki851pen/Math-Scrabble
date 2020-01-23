package de.htwg.se.scrabble.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.scrabble.util.{Command, Memento}

import scala.util.{Failure, Success}

class SetCommand(row: Int, col: Int, index: Int, controller: Controller) extends Command {
  var pastStates: List[Memento] = Nil
  var futureStates: List[Memento] = Nil
  override def doStep: Unit = {
    pastStates = controller.createMemento() :: pastStates
    futureStates = Nil
    controller.gameStatus.setGrid(controller, row, col, index) match {
      case Success(gameField) =>
        controller.setGameField(gameField)
        controller.addToSum(controller.cell(row, col).getPoint)
      case Failure(f) => println(f.getMessage)
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
