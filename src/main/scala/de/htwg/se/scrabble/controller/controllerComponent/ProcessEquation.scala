package de.htwg.se.scrabble.controller.controllerComponent

import de.htwg.se.scrabble.controller.controllerComponent.GameStatus.P
import de.htwg.se.scrabble.model.gridComponent.CellInterface

//import scala.tools.reflect.ToolBox
//TODO: check if equation is valid then calculate the point
// idea have a getrow and getcol in grid
// when set save the position in collection
// when submit check every row and col of set position
// look for not set cell  for seperation between equation
// only if in row all col have min 3 Card contiguous then is equation
// parse card to arithmetic
// check if eqution is valid
// calculate point
// remove blank
// increase hand size and pile size

case class ProcessEquation(controller: ControllerInterface){
  var newcell: List[(Int, Int)] = Nil
  controller.gameStatus match {
    case p: P => newcell = p.getNewCells
    case _ => "cant process equation"
  }
  val rows: List[Seq[CellInterface]] = newcell.map(c => c._1).map(controller.getGameField.grid.getRow(_)).distinct
  val cols: List[Seq[CellInterface]] = newcell.map(c => c._2).map(controller.getGameField.grid.getCol(_)).distinct

  val test: List[List[Seq[CellInterface]]] = rows.map(x => findEquation(List(x)))
  val test2: List[List[Seq[CellInterface]]] = cols.map(x => findEquation(List(x)))
  test.foreach(println(_))
  test2.foreach(println(_))

  def findEquation(lol: List[Seq[CellInterface]]): List[Seq[CellInterface]] = {
    val rv = lol.reverse
    val last = rv.head
    val wolast = rv.tail.reverse
    val x = last.map(_.isSet).indexOf(false)

    val c = if (x==0) last.splitAt(1) else last.splitAt(x)
    val list: List[Seq[CellInterface]] = c._1::c._2::Nil
    val newlol = wolast ::: list.filter(_.length >= 3)
    if (newlol.forall(_.forall(_.isSet))) {newlol} else findEquation(newlol) //.forall(!_.contains(""))
  }



  /*def doit(a:List[Vector[CellInterface]]) = {
    a.map(_.mkString("").trim().replaceAll(" +", " ").replaceAll("x2|x3", ""))
      .map(_.split(" ").filter(_.length >= 3).toList).distinct.flatten
  }
  val r = doit(rows)
  val c = doit(cols)
  val equationList: List[String] = r ::: c

  equationList.foreach(println(_))*/

  /*
  val expression = "5 - 3 + 2"

  def evaluate(expression: List[String]): Int = expression match {
    case l :: "+" :: r :: rest => evaluate((l.toInt + r.toInt).toString :: rest)
    case l :: "-" :: r :: rest => evaluate((l.toInt - r.toInt).toString :: rest)
    case value :: Nil => value.toInt
  }



  val expr = "2*(2+3)"*/

//  val toolbox = currentMirror.mkToolBox()
//  val calc = toolbox.eval(toolbox.parse(expr))
}
