package konopski

import akka.actor.Actor
import konopski.WordLen._

object WordLen {
  final case class GetLast()
  final case class LastLen(len: Int)
  final case class Len(len: Int)
}

class WordLen(val countLettersOnly: Boolean) extends Actor {
  var nonWsCharsBefore: Int = 0

  override def receive: Receive = {
    case c: Char => {
      if(c.isWhitespace) {
        sender ! Len(nonWsCharsBefore)
        nonWsCharsBefore = 0
      } else if(countLettersOnly) {
        if(c.isLetter) nonWsCharsBefore += 1
      }
      else nonWsCharsBefore += 1
    }
    case GetLast() => sender ! LastLen(nonWsCharsBefore)
  }
}
