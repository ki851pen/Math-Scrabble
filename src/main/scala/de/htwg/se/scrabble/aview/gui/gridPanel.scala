package de.htwg.se.scrabble.aview.gui

import de.htwg.se.scrabble.controller.Controller

import scala.swing.Swing.LineBorder
import scala.swing.event.Event
import scala.swing.{Button, Color, Dimension, GridPanel, Label}

class CellClicked(val row: Int, val column: Int) extends Event

class gridPanel (controller: Controller) extends GridPanel(controller.gridSize,controller.gridSize){
  border = LineBorder(customBlue, 2)
  background = customLightBlue
  val btnSize = new Dimension(50,50)

  val customBlue = new Color(50, 100, 200)
  val customLightBlue = new Color(190, 230, 230)
  val WHITE = new Color(255, 255, 255)

  var cells = Array.ofDim[swing.Button](controller.gridSize, controller.gridSize)
  var highlightedCell: Button = _

  def paintField {
    contents += new Label(){
      preferredSize = btnSize
      text = ""
      background = WHITE
    }
    for (col <- 0 until controller.gridSize-1) {
      contents += new Label(){
        preferredSize = btnSize
        text = (col + 65).toChar.toString
        background = WHITE
      }}
    for (row <- 0 until controller.gridSize-1) {
      contents += new Label(){
        preferredSize = btnSize
        text = (1+row).toString
        background = WHITE
      }
      for (col <- 0 until controller.gridSize-1) {
          val button = new Button() {
            preferredSize = btnSize
            background = customBlue
            val cell = controller.cell(col, row)
            if (cell.isSet) text = cell.toString
            else text = ""
          }
        cells(col)(row) = button
        contents += button
        listenTo(button)
      }
    }
  }
  paintField
  visible = true
  }