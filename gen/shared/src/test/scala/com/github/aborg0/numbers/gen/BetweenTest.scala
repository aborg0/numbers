package com.github.aborg0.numbers.gen

import org.scalatest.freespec.AnyFreeSpec

class BetweenTest extends AnyFreeSpec {
  val between: Between = BetweenFactory()
  for (i <- 0 to 20) {
    for (j <- 0 until i) {
      print(between.betweenAll(j, i))
      print("\t")
    }
    println
  }
  println
  for (i <- 0 to 20) {
    for (j <- 0 until i) {
      print(between.betweenUnique(j, i))
      print("\t")
    }
    println
  }

  println

  // Carmichael number 561
  for (j <- 0 until 561 / 2) {
    print(between.betweenAll(j, 561))
    print('\t')
  }

  "edges are zero for betweenAll" in {
    for (i <- 0 to 1000) {
      assert(between.betweenAll(0, i) === 0, s"$i")
    }
    for (i <- 1 to 1000) {
      assert(between.betweenAll(i - 1, i) === 0, s"$i")
    }
  }
  "edges are zero for betweenUnique" in {
    for (i <- 0 to 1000) {
      assert(between.betweenUnique(0, i) === 0, s"$i")
    }
    for (i <- 1 to 1000) {
      assert(between.betweenUnique(i - 1, i) === 0, s"$i")
    }
  }

  "betweenAll is symmetric" in {
    for (i <- 1 to 500) {
      for (j <- 1 to i / 2) {
        assert(between.betweenAll(j, i) === between.betweenAll(i - 1 - j, i), s"denom: $i, num: $j ${i - j}")
      }
    }
  }

  "betweenAll middles are half or ~quaters" in {
    for (i <- 1 to 1000) {
      assert(between.betweenAll(i / 2, i) === (if (i % 2 == 0) i / 4 else i / 2), s"denom: $i")
    }
  }

  "betweenUnique is symmetric" in {
    for (i <- 1 to 500) {
      for (j <- 1 to i / 2) {
        assert(between.betweenUnique(j, i) === between.betweenUnique(i - 1 - j, i), s"denom: $i, num: $j ${i - j}")
      }
    }
  }

  "betweenUnique middles are 1 or ~quaters" in {
    for (i <- 2 to 1000) {
      assert(between.betweenUnique(i / 2, i) === (if (i % 2 == 0) i / 4 else 1), s"denom: $i")
    }
  }

}
