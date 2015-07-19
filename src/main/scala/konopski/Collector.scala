package konopski

import akka.actor.UntypedActor

class Collector extends UntypedActor {
  override def onReceive(message: Any)= println(message)
}
