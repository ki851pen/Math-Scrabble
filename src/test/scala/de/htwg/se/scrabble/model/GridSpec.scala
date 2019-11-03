package de.htwg.se.scrabble.model

import org.scalatest.{Matchers, WordSpec}

class GridSpec extends WordSpec with Matchers {
  "A Grid is the playing field of Scrabble. A Grid" when {
    "to be constructed" should {
      "be created with the length of its edges as size." in {
        val grid = new Grid(9)
      }
      "for test purposes only created with a Matrix of Cells" in {
        val firstGrid = Grid(new Matrix(5, new Cell("")))
        val secondGrid = Grid(Matrix[Cell](Vector(Vector(new Cell(""),
          new Cell("")), Vector(new Cell(""), new Cell("")))))
      }
    }
    "created properly but empty" should {
      val grid = new Grid(5)
      "give access to its Cells" in {
        grid.cell(0, 0) should be(new Cell(""))
      }
      "allow to set individual Cells and remain immutable" in {
        val newGrid = grid.set(0, 0, "1")
        newGrid.cell(0, 0) should be(new Cell("1"))
        grid.cell(0,0) should be(new Cell(""))
      }
    }
  }
}
