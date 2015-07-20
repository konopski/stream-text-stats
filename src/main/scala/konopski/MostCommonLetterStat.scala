package konopski

import scala.collection.mutable

class MostCommon {
  val histogram = mutable.HashMap[Char, Int]()

  def mostCommon(): Option[Char] = {
    histogram.toList.filter(_._1.isLetter).sortBy( _._2 ).reverse.headOption.map(_._1)
  }

  def count(c: Char): Unit = {
    val occurences = histogram.get(c)
    histogram(c) = 1 + occurences.getOrElse(0)
  }
}

class MostCommonLetterStat extends BaseStat {
  val mc = new MostCommon()

  override def collectedStats: String = {
    "Most common letter: " + mc.mostCommon().getOrElse("not found")
  }

  override def nextChar(c: Char) = {
    mc.count(c)
  }
}
