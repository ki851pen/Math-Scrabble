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
  }
}
