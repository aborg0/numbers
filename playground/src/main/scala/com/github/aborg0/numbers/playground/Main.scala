package com.github.aborg0.numbers.playground

import com.github.aborg0.numbers.gen.BetweenFactory

object Main extends App {
  val between = BetweenFactory()
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

  println

  // Mersenne non-prime: 63
  for (j <- 0 until 63 / 2) {
    print(between.betweenAll(j, 63))
    print('\t')
  }

  println
}
