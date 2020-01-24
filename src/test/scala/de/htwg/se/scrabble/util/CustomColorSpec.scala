package de.htwg.se.scrabble.util

import org.scalatest.{Matchers, WordSpec}

import scala.swing.Color

class CustomColorSpec extends WordSpec with Matchers {
  "A Custom Color" should {
    "be a color" in {
      CustomColors.Green shouldBe a[Color]
      CustomColors.Blue shouldBe a[Color]
      CustomColors.LightBlue shouldBe a[Color]
      CustomColors.White shouldBe a[Color]
      CustomColors.Red shouldBe a[Color]
      CustomColors.Yellow shouldBe a[Color]
    }
  }
}