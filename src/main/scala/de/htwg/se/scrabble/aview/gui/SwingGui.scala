package de.htwg.se.scrabble.aview.gui

import de.htwg.se.scrabble.controller.Controller

import scala.swing.event.ButtonClicked
import scala.swing._

class SwingGui(controller: Controller) extends MainFrame {

}

object HelloWorld extends SimpleSwingApplication {
  def top: MainFrame = new MainFrame {
    title = "Math Scrabble"
    object submit extends Button {text = "submit"}
    contents = new FlowPanel {
      contents += new Label {
        text = " This is a label "
      }
      contents += submit
      border = Swing.EmptyBorder(20, 15, 20, 15)
    }
    listenTo(submit)
    reactions += {
      case ButtonClicked(submit) =>
        submit.text = "submitted"
        contents = new FlowPanel{
          contents += new Label {
            text = " submitted "
          }
          border = Swing.EmptyBorder(20, 15, 20, 15)
        }
    }
  }
}

