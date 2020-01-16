package de.htwg.se.scrabble.model.fileIoComponent.fileIoXmlImpl

import com.google.inject.Guice
import de.htwg.se.scrabble.ScrabbleModule
import de.htwg.se.scrabble.controller.controllerComponent.GameStatus._
import de.htwg.se.scrabble.model.fileIoComponent.FileIOInterface
import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl.GameField
import de.htwg.se.scrabble.model.gridComponent.gridBaseImpl.{Card, Cell, Grid}
import de.htwg.se.scrabble.model.gridComponent.{CardInterface, GridInterface}
import de.htwg.se.scrabble.model.pileComponent.PileBaseImpl.Pile
import de.htwg.se.scrabble.model.playerComponent.PlayerInterface
import de.htwg.se.scrabble.model.playerComponent.playerBaseImpl.Player
import de.htwg.se.scrabble.util.Memento
import net.codingwell.scalaguice.InjectorExtensions._

import scala.xml.PrettyPrinter

class FileIO extends FileIOInterface{
  override def load: Memento = {
    val file = scala.xml.XML.loadFile("gameField.xml")
    val injector = Guice.createInjector(new ScrabbleModule)

    val statusAttr = file \\ "gamefield" \\ "@status"
    val sumAttr = file \\ "gamefield" \\ "@currentsum"
    val pilecard = file \\ "pile" \\ "card"
    val sizeAttr = file \\ "grid" \\ "@size"

    val playerListNodes = file \\ "playerlist"
    val playerNameAttr = playerListNodes \\ "@name"
    val pointAttr = playerListNodes \\ "@point"
    val hands = playerListNodes \\ "hand"

    var playerList: Map[String, Player] = Map[String, Player]()
    (playerNameAttr, pointAttr, hands).zipped.foreach((x1,x2,x3) =>{
      var hand: List[CardInterface] = Nil
      val cards = x3 \\ "card"
      cards.foreach(x => hand = Card(x.text) :: hand)
      val player = Player(x1.text, hand, x2.text.toInt)
      playerList = playerList + (x1.text -> player)
    })

    println(playerList)
    val tilepile = pilecard.map(x => Card(x.text)).toList
    val pile = Pile(tilepile)
    println(pile)
    var grid: GridInterface = new Grid(sizeAttr.text.toInt).initSpecialCell
    val cellNodes = file \\ "cell"
    for (cell <- cellNodes) {
      val row: Int = (cell \ "@row").text.toInt
      val col: Int = (cell \ "@col").text.toInt
      val value = (cell \\ "card").text
      grid = grid.set(row, col, value)
    }

    val gamefield = GameField(grid,pile,playerList)
    val status = statusAttr.text match {
      case "init"=> Init()
      case "fc"=> FirstCard()
      case "pA"=> P("A")
      case "pB"=> P("B")
    }
    Memento(gamefield, status, sumAttr.text.toInt)
  }

  override def save(mem: Memento): Unit = saveString(mem)

  def saveString(mem: Memento): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("gameField.xml"))
    val prettyPrinter = new PrettyPrinter(120,4)
    val xml = prettyPrinter.format(gameFieldToXml(mem))
    pw.write(xml)
    pw.close()
  }

  def gameFieldToXml(mem: Memento) = {
    val gameField = mem.gameField
    <gamefield status={mem.gameStatus.toString} currentsum={mem.currentSum.toString}>
      <grid size={ gameField.grid.size.toString }>
      {
        for {
          row <- 0 until gameField.grid.size
          col <- 0 until gameField.grid.size
        } yield cellToXml(gameField.grid, row, col)
      }
    </grid>
      <pile>
        {gameField.pile.tilepile.map(cardToXml)}
      </pile>
      <playerlist>
        {gameField.playerList.map(playerToXml)}
      </playerlist>
    </gamefield>
  }


  def cardToXml(card: CardInterface) = {
    println(card)
    <card>{card.toString}</card>
  }

  def cellToXml(grid: GridInterface, row: Int, col: Int) = {
    <cell row={row.toString} col={col.toString}>
      <card>{grid.cell(row,col).card.toString}</card>
    </cell>
  }

  def playerToXml(player: (String,PlayerInterface)) ={
    <player name={player._1} point={player._2.point.toString}>
      <hand>{player._2.hand.map(cardToXml)}</hand>
    </player>
  }
}
