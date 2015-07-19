import akka.stream.actor.MaxInFlightRequestStrategy

object OneInFlightRequestStrategy {
  val maxInFlight = 1025

  val strategy = new MaxInFlightRequestStrategy(max = maxInFlight) {
    override def inFlightInternally = 1
  }

}
