package com.github.aborg0.numbers.gen.help

import scala.annotation.tailrec

case class Rational private(num: Int, denom: Int)

object Rational {
  def apply(num: Int, denom: Int): Rational = {
    val c = Gcd.gcd(num, denom)
    Rational(num / c, denom / c)
  }
}