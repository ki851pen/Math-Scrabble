package de.htwg.se.scrabble.controller.controllerComponent

import de.htwg.se.scrabble.controller.controllerComponent.GameStatus.P
import de.htwg.se.scrabble.model.gridComponent.CellInterface
import collection.mutable.ListBuffer

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

case class ProcessEquation(controller: ControllerInterface) {
  var newcell: List[(Int, Int)] = Nil
  controller.gameStatus match {
    case p: P => newcell = p.getNewCells
    case _ => "cant process equation"
  }
  val rows: List[Seq[CellInterface]] = newcell.map(c => c._1).map(controller.getGameField.grid.getRow(_)).distinct
  val cols: List[Seq[CellInterface]] = newcell.map(c => c._2).map(controller.getGameField.grid.getCol(_)).distinct

  val test: List[List[Seq[CellInterface]]] = rows.map(x => findEquation(List(x))) ::: cols.map(x => findEquation(List(x)))
  //test.foreach(println(_))
  //test2.foreach(println(_))
  val equations: List[Seq[CellInterface]] = test.flatten
  println(equations)
  val ret: Boolean = equations.forall(evalEquation)

  def isValid: Boolean = ret

  def findEquation(lol: List[Seq[CellInterface]]): List[Seq[CellInterface]] = {
    val rv = lol.reverse
    val last = rv.head
    val wolast = rv.tail.reverse
    val x = last.map(_.isSet).indexOf(false)

    val c = if (x == 0) last.splitAt(1) else last.splitAt(x)
    val list: List[Seq[CellInterface]] = c._1 :: c._2 :: Nil
    val newlol = wolast ::: list.filter(_.length >= 3)
    if (newlol.forall(_.forall(_.isSet))) {
      newlol
    } else findEquation(newlol) //.forall(!_.contains(""))
  }

  def splitBySeparator[T](l: Seq[T], sep: T): Seq[Seq[T]] = {
    val b = ListBuffer(ListBuffer[T]())
    l foreach { e =>
      if (e == sep) {
        if (b.last.nonEmpty) b += ListBuffer[T]()
      }
      else b.last += e
    }
    b.map(_.toSeq).toSeq
  }

  def evalEquation(equation: Seq[CellInterface]): Boolean = {
    var valList: List[Int] = Nil
    val operator = Seq("+", "-", "*", "/")
    val strEqa: Seq[String] = equation.map(c => c.card.toString)
    if (!strEqa.contains("=")) {
      false
    } else {
      val expList = splitBySeparator(strEqa, "=")
      expList.foreach(exp => {
        if (exp.exists(c => operator.exists(_ contains c))) {

        } else {
          valList = exp.mkString("").toInt :: valList
          println(valList)
        }
      })
      if (valList.forall(_ == valList.head)) {
        true
      } else {
        false
      }
    }
  }
}
  /*
  val expression = "5 - 3 + 2"

  def evaluate(expression: List[String]): Int = expression match {
    case l :: "+" :: r :: rest => evaluate((l.toInt + r.toInt).toString :: rest)
    case l :: "-" :: r :: rest => evaluate((l.toInt - r.toInt).toString :: rest)
    case value :: Nil => value.toInt
  }*/

