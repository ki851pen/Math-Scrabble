package de.htwg.se.scrabble.aview.gui

import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.cell.Cell
import de.htwg.se.scrabble.util.CustomColors

import scala.swing.event._
import scala.swing.{Button, Dimension, GridPanel}

class MyGridPanel (controller: Controller) extends GridPanel(controller.gridSize,controller.gridSize){
  vGap = 0
  val btnSize = new Dimension(48,48)
  var cells = Array.ofDim[swing.Button](controller.gridSize, controller.gridSize)
  var highlightedCell: Button = _

  def paintField {
    for (row <- 1 until controller.gridSize+1) {
      for (col <- 1 until controller.gridSize+1) {
        val button: Button = new Button() {
          val cell: Cell = controller.cell(row-1, col-1)
          cell.cellType match {
            case "d" => background = CustomColors.Yellow
            case "n" => background = CustomColors.LightBlue
            case "t" => background = CustomColors.Red
          }
          preferredSize = btnSize
          if (cell.isSet) text = cell.toString
          else text = ""
        }
        cells(row-1)(col-1) = button
        contents += button
        listenTo(button)
        reactions += {
          case ButtonClicked(b) if b == button => controller.changeClick(row, col)
        }
      }
    }
  }
  paintField
  visible = true


  def redraw = {
    for (row <- 1 until controller.gridSize+1) {
      for (col <- 1 until controller.gridSize+1) {
        val cell = cells(row-1)(col-1)
        cell.text = {
          val c: Cell = controller.cell(row-1,col-1)
          if (c.isSet) c.toString else ""
        }
      }
    }
    repaint
  }
}