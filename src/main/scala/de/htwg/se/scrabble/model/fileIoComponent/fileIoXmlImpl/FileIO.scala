package de.htwg.se.scrabble.model.fileIoComponent.fileIoXmlImpl

import de.htwg.se.scrabble.model.fileIoComponent.FileIOInterface
import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gridComponent.CardInterface

import scala.xml.PrettyPrinter

class FileIO extends FileIOInterface{
  override def load: GameFieldInterface = ???

  override def save(gameField: GameFieldInterface): Unit = saveString(gameField)

  def saveString(gameField: GameFieldInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("gameField.xml"))
    val prettyPrinter = new PrettyPrinter(120,4)
    val xml = prettyPrinter.format(gameFieldToXml(gameField))
    pw.write(xml)
    pw.close()
  }

  def gameFieldToXml(gameField: GameFieldInterface) = {
    <gamefield>
      <grid>
        <cell></cell>
      </grid>
      <pile>
        {
          gameField.pile.tilepile.foreach(cardToXml)
        }
      </pile>
      <playerlist>
      </playerlist>
    </gamefield>
  }

  def cardToXml(card: CardInterface) = {
    <card>{card.toString}</card>
  }
}
