package de.htwg.se.scrabble.controller
/*
import de.htwg.se.scrabble.controller.GameStatus.P
import de.htwg.se.scrabble.model.cell.Cell
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox

case class ProcessEquation(controller: Controller){
  var newcell: List[(Int, Int)] = Nil
  controller.gameStatus match {
    case p: P => newcell = p.getNewCells
    case _ => "cant process equation"
  }
  val rows: List[Vector[Cell]] = newcell.map(c => c._1).map(controller.getGameField.grid.getRow(_))
  val cols: List[Vector[Cell]] = newcell.map(c => c._2).map(controller.getGameField.grid.getCol(_))
  println(rows)
  println(cols)

  def doit(a:List[Vector[Cell]]) = {
    a.map(_.mkString("").trim().replaceAll(" +", " ").replaceAll("x2|x3", ""))
      .map(_.split(" ").filter(_.length >= 3).toList).distinct.flatten
  }
  val r = doit(rows)
  val c = doit(cols)
  val equationList: List[String] = r ::: c

  equationList.foreach(println(_))


  val expression = "5 - 3 + 2"

  def evaluate(expression: List[String]): Int = expression match {
    case l :: "+" :: r :: rest => evaluate((l.toInt + r.toInt).toString :: rest)
    case l :: "-" :: r :: rest => evaluate((l.toInt - r.toInt).toString :: rest)
    case value :: Nil => value.toInt
  }



  val expr = "2*(2+3)"

  val toolbox = currentMirror.mkToolBox()
  val calc = toolbox.eval(toolbox.parse(expr))


//TODO: check if equation is valid then calculate the point
  // idea have a getrow and getcol in grid
  // when set save the position in collection
  // when submit check every row and col of set position
  // look for not set cell  for seperation between equation
  // only if in row all col have min 3 Card contiguous then is equation
  // parse card to arithmetic
  // check if eqution is valid
  // calculate point
}*/
