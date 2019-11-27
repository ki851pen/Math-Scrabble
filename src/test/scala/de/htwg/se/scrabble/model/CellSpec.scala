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
      "have a String representation" in {
        testcell.toString shouldBe " "
        testcell2.toString shouldBe "x2"
        testcell3.toString shouldBe "x3"
      }
    }
    "set correctly" should {
      val testcell = Cell("")
      val testdoublecell = Cell("d","")
      val testtripplecell = Cell("t","")
      val changedcell = testcell.setCell("1")
      val changeddoublecell = testdoublecell.setCell("7")
      val changedtripplecell = testtripplecell.setCell("?")
      "have changed value" in {
        changedcell.card shouldBe Card("1")
        changeddoublecell.card shouldBe Card("7")
        changedtripplecell.card shouldBe Card("?")
      }
      "be set" in {
        changedcell.isSet shouldBe true
        changeddoublecell.isSet shouldBe true
        changedtripplecell.isSet shouldBe true
      }
      "have a point assign to them" in {
        changedcell.getPoint shouldBe 1
        changeddoublecell.getPoint shouldBe 8
        changedtripplecell.getPoint shouldBe 0
      }
      "have a String representation" in {
        changedcell.toString shouldBe "1"
        changeddoublecell.toString shouldBe "7x2"
        changedtripplecell.toString shouldBe "?x3"
      }
    }
    "set incorrectly" should {
      val testcell = Cell("")
      val changedcell = testcell.setCell("d")
      "have old value" in {
        changedcell.card shouldBe Card("")
      }
      "not be set" in {
        changedcell.isSet shouldBe false
      }
      "have a point assign to them" in {
        changedcell.getPoint shouldBe 0
      }
    }
    "check equality" should {
      val testcell = Cell("")
      val testcell2 = Cell("n","")
      val testcell3 = Cell("d","")
      "be equal with same type" in {
        testcell shouldEqual testcell2
      }
      "not equal with different type" in {
        testcell should not equal testcell3
      }
    }
  }
}
