package com.github.aborg0.numbers.gen

import com.github.aborg0.numbers.gen.Between.{AssumeSymmetric, Impl}

object BetweenFactory {

  private class WeakCaching(impl: Between) extends Between {

    import scala.collection.mutable.{WeakHashMap => WeakHM}

    private val cacheAll: WeakHM[Int, WeakHM[Int, Int]] = WeakHM()
    private val cacheUnique: WeakHM[Int, WeakHM[Int, Int]] = WeakHM()

    override def betweenAll(start: Int, denominator: Int): Int = cacheAll.synchronized {
      val inner = cacheAll.getOrElseUpdate(denominator, WeakHM())
      inner.synchronized {
        inner.getOrElseUpdate(start, impl.betweenAll(start, denominator))
      }
    }

    override def betweenUnique(start: Int, denominator: Int): Int = cacheUnique.synchronized {
      val inner = cacheUnique.getOrElseUpdate(denominator, WeakHM())
      inner.synchronized {
        inner.getOrElseUpdate(start, impl.betweenUnique(start, denominator))
      }
    }
  }
  def apply(): Between = new AssumeSymmetric(new WeakCaching(new Impl))
}
