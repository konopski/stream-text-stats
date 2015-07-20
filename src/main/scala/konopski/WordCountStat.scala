package konopski

import akka.actor.{ActorRef, Props}
import akka.stream.actor.ActorSubscriberMessage.{OnNext, OnComplete}

class WordCountStat extends BaseStat {
  var wordCount = 0
  var wordLen: ActorRef = null

  override def preStart() = {
    wordLen = context.actorOf(Props[WordLen], "word-len-for-word-count")
  }

  override def nextChar(ch: Char) = { wordLen ! ch }

  override def collectedStats = "Number of words: " + wordCount

  override def receive = {
    case WordLen.Len(l) => {
      incWordCountIfNonZeroLen(l)
    }
    case l: Int => {
      incWordCountIfNonZeroLen(l)
    }
    case WordLen.LastLen(l) => {
      incWordCountIfNonZeroLen(l)
      sendStats()
      terminate()
    }
    case OnComplete => {
      logger.debug("the end of word count")
      wordLen ! WordLen.GetLast()
    }
    case OnNext(c) => {
      c match {
        case ch: Char => nextChar(ch)
      }
    }
    case _ => super.receive
  }

  def incWordCountIfNonZeroLen(l: Int): Unit = {
    if (l > 0) wordCount += 1
  }
}
