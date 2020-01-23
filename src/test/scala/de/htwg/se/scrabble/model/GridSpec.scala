package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.gridComponent.gridBaseImpl.{Cell, Grid}
import org.scalatest.{Matchers, WordSpec}

class GridSpec extends WordSpec with Matchers {
  "A Grid is the playing field of Scrabble. A Grid" when {
    "create with size" should {
      val grid = new Grid(15)
      "be empty" in {
        grid.isEmpty should be(true)
      }
      "give access to its Cells" in {
        grid.cell(0, 0) shouldEqual Cell("")
      }
      "have a string representation" in {
        grid.toString shouldBe a [String]
      }
      "allow to set individual Cells and remain immutable" in {
        val newGrid = grid.set(0, 0, "1")
        newGrid.cell(0, 0) shouldEqual Cell("1")
        grid.cell(0,0) shouldEqual Cell("")
      }
      "be able to get list of cell in row or column" in {
        val grid2 = new Grid(3)
        val newGrid2 = grid2.set(0, 0, "1")
        newGrid2.getCol(0) shouldBe List(Cell("1"), Cell(""), Cell(""))
        newGrid2.getRow(0) shouldBe List(Cell("1"), Cell(""), Cell(""))
      }
    }
    "set" should {
      val newGrid = new Grid(3)
      val setGrid = newGrid.set(0,2,"3")
      "not be empty" in {
        setGrid.isEmpty should be(false)
      }
    }
    "created for test purposes only with a Vector of Vectors" should {
      val testGrid = Grid(Vector(Vector(Cell(""))))
      "work normally" in {
        testGrid.size should be (1)
      }
    }
  }
}
