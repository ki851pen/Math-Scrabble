package de.htwg.se.scrabble.model.fileIoComponent.fileIoJsonImpl

import java.io.{File, PrintWriter}

import de.htwg.se.scrabble.controller.controllerComponent.GameStatus.{FirstCard, Init, P}
import de.htwg.se.scrabble.model.fileIoComponent.FileIOInterface
import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl.GameField
import de.htwg.se.scrabble.util.Memento
import play.api.libs.json.Json

import scala.io.Source

class FileIO extends FileIOInterface {
  override def load: Memento = {
    val file = Json.parse(Source.fromFile("game.json").getLines().mkString)
    val statusValue = file \ "status"
    val currentSumValue = file \ "currentsum"
    val gameFieldValue = file \ "gameField"
    val status = statusValue.as[String] match {
      case "init"=> Init()
      case "fc"=> FirstCard()
      case "pA"=> P("A")
      case "pB"=> P("B")
    }
    val gf: GameFieldInterface = gameFieldValue.as[GameField]

    Memento(gf, status, currentSumValue.as[Int])
  }

  override def save(mem: Memento): Unit = {
    val pw = new PrintWriter(new File("game.json"))
    pw.write(Json.prettyPrint(memToJson(mem)))
    pw.close
  }

  def memToJson(mem: Memento) = {
    val gf = mem.gameField.asInstanceOf[GameField]
     Json.obj(
    "gameField" -> Json.toJson(gf),
    "status" -> Json.toJson(mem.gameStatus.toString),
       "currentsum" -> Json.toJson(mem.currentSum)
    )
  }
}
