import akka.actor.ActorLogging
import akka.stream.actor.ActorSubscriber
import akka.stream.actor.ActorSubscriberMessage._

class PrintCharActor extends ActorSubscriber with ActorLogging {

  override def receive = {
    case OnNext(c) => print(c)

    case OnComplete => println("the end"); context.stop(self)

    case OnError(cause) => println("error:" + cause)

  }

  override protected def requestStrategy = OneInFlightRequestStrategy.strategy
}
