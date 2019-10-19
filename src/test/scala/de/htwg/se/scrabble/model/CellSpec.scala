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
      val notvalidcell = Cell("+")
      "have value +" in {
        notvalidcell.value should be("+")
      }
      "is valid" in{
        notvalidcell.isvalid should be(true)
      }
    }
    "set to n" should {
      val notvalidcell = Cell("n")
      "have value n" in {
        notvalidcell.value should be("n")
      }
      "is not valid" in{
        notvalidcell.isvalid should be(false)
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
