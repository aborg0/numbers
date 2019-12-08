package com.github.aborg0.numbers.gen

import com.github.aborg0.numbers.gen.Between.{AssumeSymmetric, Impl, UnsafeCaching}

object BetweenFactory {
  def apply(): Between = new AssumeSymmetric(new UnsafeCaching(new Impl))
}
