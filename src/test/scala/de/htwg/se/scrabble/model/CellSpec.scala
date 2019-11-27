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
    "have changed value" in {
      changedcell.card shouldBe ""
    }
    "have a point assign to them" in {
      changedcell.getPoint shouldBe 0
    }
  }
}/*
"A Cell" when {
  "not set to any value " should {
    val emptyCell = new Cell("")
    "have value \"\"" in {
      emptyCell.value should be ("")
    }
    "not be set" in {
      emptyCell.isSet should be (false)
    }
  }
  "set to a specific value" should {
    val nonEmptyCell = new Cell("5")
    val othernonEmptyCell = Cell("5", Double)

    "return that value" in {
      nonEmptyCell.value should be("5")
      othernonEmptyCell.value should be("5")
    }
    "be set" in {
      nonEmptyCell.isSet should be(true)
      othernonEmptyCell.isSet should be(true)
    }
    "have string representation" in {
      nonEmptyCell.toString should be ("5")
    }
  }
  "special" should{
    val specialCell = Cell("1", Triple)
    val normalCell = Cell("1", Normal)
    "be not normal" in {
      specialCell.isNormal should be(false)
      normalCell.isNormal should be(true)
    }
  }
}*/
}
