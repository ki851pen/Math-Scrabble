package de.htwg.se.scrabble.util

class UndoManager {
  private var undoStack: List[Command]= Nil
  private var redoStack: List[Command]= Nil
  def resetStack(): Unit = {
    undoStack = Nil
    redoStack = Nil
  }
  def doStep(command: Command): Unit = {
    undoStack = command::undoStack
    command.doStep
  }
  def undoStep: Unit = {
    undoStack match {
      case Nil => println("can't undo")
      case head::stack =>
        head.undoStep
        undoStack=stack
        redoStack= head::redoStack
    }
  }
  def redoStep: Unit = {
    redoStack match {
      case Nil => println("can't redo")
      case head::stack =>
        head.redoStep
        redoStack=stack
        undoStack=head::undoStack
    }
  }
}
