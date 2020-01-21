package de.htwg.se.scrabble.controller.controllerComponent

import de.htwg.se.scrabble.controller.controllerComponent.GameStatus.P
import de.htwg.se.scrabble.model.gridComponent.CellInterface

import collection.mutable.ListBuffer

import com.github.pdorobisz.mathexpressionevaluator.Evaluator
import com.github.pdorobisz.mathexpressionevaluator.errors.EvaluatorError

import scala.util.{Failure, Success}

case class ProcessEquation(controller: ControllerInterface) {
  var newcell: List[(Int, Int)] = Nil
  controller.gameStatus match {
    case p: P => newcell = p.getNewCells
    case _ => "cant process equation"
  }
  val rows: List[Seq[CellInterface]] = newcell.map(c => c._1).map(controller.getGameField.grid.getRow(_)).distinct
  val cols: List[Seq[CellInterface]] = newcell.map(c => c._2).map(controller.getGameField.grid.getCol(_)).distinct

  val test: List[List[Seq[CellInterface]]] = rows.map(x => findEquation(List(x))) ::: cols.map(x => findEquation(List(x)))
  val equations: List[Seq[CellInterface]] = test.flatten
  println(equations)
  private val ret: Boolean = equations.forall(evalEquation)

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
          Evaluator.evaluate(exp.mkString("")) match {
            case Success(s) => valList = s :: valList
            case Failure(_) => return false
          }
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
