package konopski

import scala.collection.mutable

class AvgCalculator {

  val collectedLengths = new mutable.HashMap[Int, Int]()

  def average: Double = {
    if(collectedLengths.isEmpty) .0
    else {
      imperativeAvgCalc
    }
  }

  private def imperativeAvgCalc: Double = {
    var len = .0
    var occurences = 0
    for ((l, occ) <- collectedLengths) {
      len += l * occ
      occurences += occ
    }
    len / occurences
  }

  def countOccurence(l: Int): Unit = {
    if(l > 0) {
      val occurences = collectedLengths.get(l)
      collectedLengths(l) = 1 + occurences.getOrElse(0)
    }
  }

}
