package de.htwg.se.scrabble.aview.gui

import de.htwg.se.scrabble.controller.controllerComponent.{ButtonSet, ControllerInterface}
import de.htwg.se.scrabble.util.CustomColors

import scala.swing.event._
import scala.swing.{Button, Dimension, GridPanel}

class MyGridPanel(controller: ControllerInterface) extends GridPanel(controller.gridSize, controller.gridSize) {
  val btnSize = new Dimension(48, 48)
  var cells = Array.ofDim[Button](controller.gridSize, controller.gridSize)

  def paintField {
    for (row <- 1 until controller.gridSize + 1) {
      for (col <- 1 until controller.gridSize + 1) {
        val button: Button = new Button("") {
          opaque = true
          val cell = controller.cell(row - 1, col - 1)
          if (cell.isNormal) background = CustomColors.LightBlue
          else if (cell.isDouble) background = CustomColors.Yellow
          else background = CustomColors.Red
          preferredSize = btnSize
        }
        listenTo(controller)
        cells(row - 1)(col - 1) = button
        contents += button
        listenTo(button)
      }
    }
  }

  paintField
  visible = true


  def redraw = {
    for (row <- 1 until controller.gridSize + 1) {
      for (col <- 1 until controller.gridSize + 1) {
        val cell = cells(row - 1)(col - 1)
        cell.text = {
          val c = controller.cell(row - 1, col - 1)
          if (c.isSet) c.toString
          else ""
        }
      }
    }
    repaint
  }

  private def findButton(bt: Button): Either[String, (Int, Int)] = {
    for (row <- 1 until controller.gridSize + 1) {
          for (col <- 1 until controller.gridSize + 1) {
            if (cells(row - 1)(col - 1) == bt) {
              return Right(row, col)
            }
          }
    }
    Left("Unknown Button clicked!!!")
  }

  reactions += {
    case ButtonClicked(b) =>
      findButton(b.asInstanceOf[Button]) match {
        case Right((row, col)) => controller.selectedCellChanged(row, col)
        case Left(failure) => println(failure)
      }
    case ButtonSet(row, col) =>
      val bt = cells(row)(col)
      bt.text = controller.cell(row, col).toString
  }
}