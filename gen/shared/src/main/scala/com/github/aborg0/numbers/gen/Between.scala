package com.github.aborg0.numbers.gen

import com.github.aborg0.numbers.gen.help.Gcd

trait Between {
  /**
   * Number of pairs: {{(num, denom)}} where {{num/denom > start/denominator}} and {{num/denom < (start+1)/denominator}}
   * and {{denom < denominator}}
   *
   * @param start       The smaller nominator of the interval
   * @param denominator The denominator of the interval values
   * @return Number of factions (sames counted with each denominator for which they appear)
   *         with smaller denominator between start/denominator and (start+1)/denominator
   */
  def betweenAll(start: Int, denominator: Int): Int

  /**
   * Number of rationals: {{num/denom}} where {{num/denom > start/denominator}} and {{num/denom < (start+1)/denominator}}
   * and {{denom < denominator}}
   *
   * @param start       The smaller nominator of the interval
   * @param denominator The denominator of the interval values
   * @return Number of factions (each rational is counted only once)
   *         with smaller denominator between start/denominator and (start+1)/denominator
   */
  def betweenUnique(start: Int, denominator: Int): Int
}

object Between {

  private[gen] class Impl extends Between {
    override def betweenAll(start: Int, denominator: Int): Int = {
      // start / denominator < k / denom < (start + 1) / denominator
      // start * denom / denominator < k < (start + 1) * denom / denominator
      // start * denom < k * denominator < (start + 1) * denom
      (for {denom <- 1 until denominator
            nom = (start + 1) * denom.toLong / denominator
            nomDenom = nom * denominator
            if nomDenom > start * denom && nomDenom < (start + 1) * denom} yield {
        1
      }).sum
    }

    override def betweenUnique(start: Int, denominator: Int): Int =
      (for {denom <- 1 until denominator
            nom = (start + 1) * denom.toLong / denominator
            nomDenom = nom * denominator
            if nomDenom > start * denom && nomDenom < (start + 1) * denom
            if Gcd.gcd(denom, nom) == 1} yield {
        1
      }).sum
  }

  private[gen] class UnsafeCaching(impl: Between) extends Between {

    import scala.collection.mutable.{HashMap => mutHashMap}

    private val cacheAll: mutHashMap[Int, mutHashMap[Int, Int]] = mutHashMap()
    private val cacheUnique: mutHashMap[Int, mutHashMap[Int, Int]] = mutHashMap()

    override def betweenAll(start: Int, denominator: Int): Int =
      cacheAll.getOrElseUpdate(denominator, mutHashMap()).getOrElseUpdate(start, impl.betweenAll(start, denominator))

    override def betweenUnique(start: Int, denominator: Int): Int =
      cacheUnique.getOrElseUpdate(denominator, mutHashMap()).getOrElseUpdate(start, impl.betweenUnique(start, denominator))
  }

  private[gen] class AssumeSymmetric(impl: Between) extends Between {
    override def betweenAll(start: Int, denominator: Int): Int = {
      require((start < denominator && start >= 0) || (start == 0), s"start: $start, denominator: $denominator")
      if (start < denominator / 2) impl.betweenAll(start, denominator)
      else impl.betweenAll(denominator - 1 - start, denominator)
    }

    override def betweenUnique(start: Int, denominator: Int): Int = {
      require((start < denominator && start >= 0) || start == 0, s"start: $start, denominator: $denominator")
      if (start < denominator / 2) impl.betweenUnique(start, denominator)
      else impl.betweenUnique(denominator - 1 - start, denominator)
    }
  }

  private[gen] def nonCaching: Between = new Impl

  //  def apply(): Between = new AssumeSymmetric(new WeakCaching(new Impl))
}
