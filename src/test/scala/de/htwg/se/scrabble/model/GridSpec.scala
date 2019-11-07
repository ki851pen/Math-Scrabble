package de.htwg.se.scrabble.model

import org.scalatest.{Matchers, WordSpec}
//TODO:merge gridspec and matrixspec

class GridSpec extends WordSpec with Matchers {
  "A Grid is the playing field of Scrabble. A Grid" when {
    "create with size" should {
      val grid = new Grid(15)
      "be empty" in {
        grid.isEmpty should be(true)
      }
      "give access to its Cells" in {
        grid.cell(0, 0) should be(new Cell(""))
      }
      "have a string representation" in {
        grid.toString shouldBe a [String]
      }
      "allow to set individual Cells and remain immutable" in {
        val newGrid = grid.set(0, 0, "1")
        newGrid.cell(0, 0) should be(new Cell("1"))
        grid.cell(0,0) should be(new Cell(""))
      }
    }
    "set" should {
      val newGrid = new Grid(3)
      val setGrid = newGrid.set(0,2,"3")
      "not be empty" in {
        setGrid.isEmpty should be(false)
      }
    }
    "filled" should {
      val grid = new Grid(2, new Cell("1"))
      "give access to its cells" in {
        grid.cell(0, 0) should be(new Cell("1"))
      }
      "be filled using fill operation" in {
        val returnedGrid = grid.fill(new Cell("5"))
        returnedGrid.cell(0, 0) should be(new Cell("5"))
      }
      "also have a string representation" in {
        grid.toString shouldBe a [String]
      }
    }
    "created for test purposes only with a Vector of Vectors" should {
      val testGrid = Grid(Vector(Vector(new Cell(""))))
      "work normally" in {
        testGrid.size should be (1)
      }
    }
  }
}
