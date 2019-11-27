package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.cell.Cell
import org.scalatest.{Matchers, WordSpec}

class CellSpec extends WordSpec with Matchers {
  "A Cell" when {
    "instantiated without explicit type" should {
      val testcell = Cell("")
      "be normal" in {
        testcell.cellType shouldBe "n"
      }
    }
    "instantiated with explicit type" should {
      val testcell = Cell("n","")
      val testcell2 = Cell("d","")
      val testcell3 = Cell("t","")
      "have type" in {
        testcell.cellType shouldBe "n"
        testcell2.cellType shouldBe "d"
        testcell3.cellType shouldBe "t"
      }
    }
    "set correctly" should {
      val testcell = Cell("")
      val changedcell = testcell.setCell("1")
      "have changed value" in {
        changedcell.card shouldBe Card("1")
      }
      "have a point assign to them" in {
        changedcell.getPoint shouldBe 1
      }
    }
    "set incorrectly" should {
      val testcell = Cell("")
      val changedcell = testcell.setCell("d")
      "have old value" in {
        changedcell.card shouldBe Card("")
      }
      "have a point assign to them" in {
        changedcell.getPoint shouldBe 0
      }
    }
  }
}
