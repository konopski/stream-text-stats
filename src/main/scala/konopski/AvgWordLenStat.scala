package konopski


import java.text.DecimalFormat

import akka.actor.{ActorRef, Props}
import akka.stream.actor.ActorSubscriberMessage.{OnNext, OnComplete}
import konopski.WordLen._

class AvgWordLenStat extends BaseStat {
  var wordLen: ActorRef = null

  val avgCalculator = new AvgCalculator()

  override def preStart() = {
    wordLen = context.actorOf(Props(classOf[WordLen], (c: Char) => c.isLetter), "word-len-for-avg-word-len")
  }

  override def nextChar(ch: Char) = { wordLen ! ch }

  override def collectedStats = {
    val avg: java.lang.Double = avgCalculator.average
    "Avg number of letters in word: " + new DecimalFormat("##.#").format(avg)
  }

  override def receive = {
    case Len(l) => {
      incNumberOfWordsWithLen(l)
    }
    case l: Int => {
      incNumberOfWordsWithLen(l)
    }
    case LastLen(l) => {
      incNumberOfWordsWithLen(l)
      sendStats()
      terminate()
    }
    case OnComplete => {
      logger.debug("the end of avg word len")
      wordLen ! GetLast()
    }
    case OnNext(c) => {
      c match {
        case ch: Char => nextChar(ch)
      }
    }
    case _ => super.receive
  }

  def incNumberOfWordsWithLen(l: Int): Unit = {
    avgCalculator.countOccurence(l)
  }

}
