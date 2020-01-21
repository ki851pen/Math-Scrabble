package de.htwg.se.scrabble.util

import org.scalatest.{Matchers, WordSpec}
import de.htwg.se.scrabble.util.Calculator

class CalculatorSpec extends WordSpec with Matchers {
  "A Calculator" should {
    val cal = Calculator
    "be able to parse equation and return correct value" in {
      cal("3+3*2") should be (9)
      cal("4/2-2") should be (0)
    }
  }
}
