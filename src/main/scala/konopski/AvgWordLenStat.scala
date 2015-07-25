package konopski


import akka.actor.{ActorRef, Props}
import akka.stream.actor.ActorSubscriberMessage.{OnNext, OnComplete}

class AvgWordLenStat extends BaseStat {
  var wordLen: ActorRef = null

  override def preStart() = {
    wordLen = context.actorOf(Props(classOf[WordLen], (c: Char) => c.isLetter), "word-len-for-avg-word-len")
  }

  override def nextChar(ch: Char) = { wordLen ! ch }

  override def collectedStats = "Avg number of letters in word: " + "TODO. format"

  override def receive = {
    case WordLen.Len(l) => {
      incNumberOfWordsWithLen(l)
    }
    case l: Int => {
      incNumberOfWordsWithLen(l)
    }
    case WordLen.LastLen(l) => {
      incNumberOfWordsWithLen(l)
      sendStats()
      terminate()
    }
    case OnComplete => {
      logger.debug("the end of avg word len")
      wordLen ! WordLen.GetLast()
    }
    case OnNext(c) => {
      c match {
        case ch: Char => nextChar(ch)
      }
    }
    case _ => super.receive
  }

  def incNumberOfWordsWithLen(l: Int): Unit = {

  }

}
