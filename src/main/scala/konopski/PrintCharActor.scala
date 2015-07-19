package konopski

import akka.stream.actor.ActorSubscriber
import akka.stream.actor.ActorSubscriberMessage._
import com.typesafe.scalalogging.slf4j.StrictLogging

class PrintCharActor extends ActorSubscriber with StrictLogging {

  def nextChar(ch: Char) = logger.debug("received char: " + ch)

  override def receive = {
    case OnNext(c) => {
//      logger.debug(c.toString)
      c match {
        case ch: Char => nextChar(ch)
      }
    }

    case OnComplete => {
      logger.debug("the end")
      terminate()
    }

    case OnError(cause) => {
      logger.error("something bad happened", cause)
      terminate()
    }

  }

  def terminate(): Unit = {
    context.stop(self)
    context.system.actorSelection("/user/terminator") ! "i am done"
  }

  override protected def requestStrategy = OneInFlightRequestStrategy.strategy
}
