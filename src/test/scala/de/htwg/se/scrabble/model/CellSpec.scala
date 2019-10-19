package de.htwg.se.scrabble.model

import org.scalatest.{Matchers, WordSpec}

class CellSpec extends WordSpec with Matchers {

  "A Cell" when {
    "not set to any value " should {
      val emptyCell = Cell("")
      "have value \"\"" in {
        emptyCell.value should be ("")
      }
      "not be set" in {
        emptyCell.isSet("") should be (false)
      }
    }
    "set to 3" should {
      val filledcell = Cell("3")
      "have value 3" in {
        filledcell.value should be("3")
      }
    }
  }
  "A Cell" should {
    "have a valid list that contain number" in {
      val testCell = Cell("")
      testCell.validlist.contains(4) should be(true)
    }
    "and contain arithmetic operation" in {
      val testCell = Cell("")
      testCell.validlist.contains("+") should be(true)
    }
  }
}
