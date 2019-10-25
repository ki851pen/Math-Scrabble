package de.htwg.se.scrabble.model
import org.scalatest.{Matchers, WordSpec}

class MatrixSpec extends WordSpec with Matchers {
  "A Matrix is a tailor-made immutable data type that contains a two-dimensional Vector of Cells. A Matrix" when {
    "empty" should {
      "be filled with empty cell" in {
        val emptyMatrix = new Matrix[Cell] (2, new Cell(""))
        emptyMatrix.cell(1, 1).isSet should be(false)
      }
      "be created by using a dimension and a sample cell" in {
        val matrix = new Matrix[Cell](2, new Cell(""))
        matrix.size should be(2)
      }
      "for test purposes only be created with a Vector of Vectors" in {
        val testMatrix = Matrix(Vector(Vector(new Cell(""))))
        testMatrix.size should be(1)
      }
    }
    "filled" should {
      val matrix = new Matrix[Cell](2, new Cell("1"))
      "give access to its cells" in {
        matrix.cell(0, 0) should be(new Cell("1"))
      }
      "replace cells and return a new data structure" in {
        val returnedMatrix = matrix.replaceCell(0, 0, new Cell("2"))
        matrix.cell(0, 0) should be(new Cell("1"))
        returnedMatrix.cell(0, 0) should be(new Cell("2"))
      }
      "be filled using fill operation" in {
        val returnedMatrix = matrix.fill(new Cell("5"))
        returnedMatrix.cell(0, 0) should be(new Cell("5"))
      }
    }
  }

}
