package de.htwg.se.scrabble.util
import org.scalatest.{Matchers, WordSpec}

class UndoManagerSpec extends WordSpec with Matchers {
  "An UndoManager manages Undo and Undo and Redo Command" should {
    val undoManager = new UndoManager
    class testCommand() extends Command {
      var number = 0
      override def doStep: Unit = number += 1
      override def undoStep: Unit = number -= 1
      override def redoStep: Unit = number += 1
    }

    "can't undo or redo if did't do anything" in {
      val command = new testCommand
      command.number should be (0)
      undoManager.undoStep
      command.number should be (0)
      undoManager.redoStep
      command.number should be (0)
    }

    "have a do, undo and redo" in {
      val command = new testCommand
      command.number should be (0)
      undoManager.doStep(command)
      command.number should be (1)
      undoManager.undoStep
      command.number should be (0)
      undoManager.redoStep
      command.number should be (1)
    }

    "handle multiple undo steps correctly" in {
      val command = new testCommand
      command.number should be (0)
      undoManager.doStep(command)
      command.number should be (1)
      undoManager.doStep(command)
      command.number should be (2)
      undoManager.undoStep
      command.number should be (1)
      undoManager.undoStep
      command.number should be (0)
      undoManager.redoStep
      command.number should be (1)
    }
  }
}