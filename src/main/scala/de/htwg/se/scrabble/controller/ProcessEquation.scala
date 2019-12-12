package de.htwg.se.scrabble.controller

import de.htwg.se.scrabble.controller.GameStatus.P
import de.htwg.se.scrabble.model.cell.Cell

case class ProcessEquation(controller: Controller){
  var newcell: List[(Int, Int)] = Nil
  controller.gameStatus match {
    case p: P => newcell = p.getNewCells
    case _ => "cant process equation"
  }
  val rows: List[Vector[Cell]] = newcell.map(c => c._1).map(controller.getGameField.grid.getRow(_))
  val cols: List[Vector[Cell]] = newcell.map(c => c._2).map(controller.getGameField.grid.getCol(_))
  val newrows: List[String] = rows.map(_.mkString("").trim().replaceAll(" +", " ").replaceAll("x2|x3", ""))
  val newcols: List[String] = cols.map(_.mkString("").trim().replaceAll(" +", " ").replaceAll("x2|x3", ""))
  val r: List[List[String]] = newrows.map(_.split(" ").filter(_.length >= 3).toList).distinct
  val c: List[List[String]] = newcols.map(_.split(" ").filter(_.length >= 3).toList).distinct

  println(rows)
  println(cols)
  println(newrows)
  println(newcols)
  r.foreach(x => x.foreach(println(_)))
  c.foreach(x => x.foreach(println(_)))

//TODO: check if equation is valid then calculate the point
  // idea have a getrow and getcol in grid
  // when set save the position in collection
  // when submit check every row and col of set position
  // look for not set cell  for seperation between equation
  // only if in row all col have min 3 Card contiguous then is equation
  // parse card to arithmetic
  // check if eqution is valid
  // calculate point
}
