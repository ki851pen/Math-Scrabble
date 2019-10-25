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
        emptyCell.isSet should be (false)
      }
    }
    "set to 3" should {
      val validcell = Cell("3")
      "have value string 3" in {
        validcell.value should be("3")
      }
      "parse string 3 to int" in {
        validcell.parsevalue should be(3)
      }
      "is valid" in{
        validcell.isvalid should be(true)
      }
    }
    "set to +" should {
      val pluscell = Cell("+")
      "have value +" in {
        pluscell.value should be("+")
      }
      "have the same parsevulue as value" in {
        pluscell.parsevalue should be("+")
      }
      "is valid" in{
        pluscell.isvalid should be(true)
      }
    }
    "set to n" should {
      val notvalidcell = Cell("n")
      "have value n" in {
        notvalidcell.value should be("n")
      }
      "have empty string as parsevulue" in {
        notvalidcell.parsevalue should be("")
      }
      "is not valid" in{
        notvalidcell.isvalid should be(false)
      }
    }
  }
  "A Cell" should {
    "have a valid list that contain number as String" in {
      val testCell = Cell("")
      testCell.validlist.contains("0") should be(true)
    }
    "and contain arithmetic operation as String" in {
      val testCell = Cell("")
      testCell.validlist.contains("=") should be(true)
    }
  }
}
