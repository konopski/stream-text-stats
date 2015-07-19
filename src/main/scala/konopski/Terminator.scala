package konopski

import akka.actor.UntypedActor
import com.typesafe.scalalogging.slf4j.StrictLogging

class Terminator(val numOfActors: Int) extends UntypedActor with StrictLogging {

  var receivedDoneMsgs = 0

  override def onReceive(message: Any): Unit = {
    receivedDoneMsgs += 1
    if(numOfActors == receivedDoneMsgs) {
      logger.info(s"finished processing by all $numOfActors handlers.")
      context.system.shutdown()
    }
  }
}
