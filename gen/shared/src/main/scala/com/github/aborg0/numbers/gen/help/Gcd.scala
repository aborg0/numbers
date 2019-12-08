package com.github.aborg0.numbers.gen.help

import scala.annotation.tailrec

object Gcd {
  @tailrec
  def gcd(a: Int, b: Int): Int = {
    if (a < b) {
      gcd(b, a)
    } else {
      if (a % b == 0) {
        b
      } else {
        gcd(b, a % b)
      }
    }
  }
  @tailrec
  def gcd(a: Long, b: Long): Long = {
    if (a < b) {
      gcd(b, a)
    } else {
      if (a % b == 0) {
        b
      } else {
        gcd(b, a % b)
      }
    }
  }

}
