package konopski

import akka.actor.{ActorSystem, Props}
import akka.stream._
import akka.stream.scaladsl._
import com.typesafe.scalalogging.slf4j.StrictLogging

/** solution for Task 20150701 */
object TextStatisticsApp extends App with StrictLogging {

  implicit val system = ActorSystem("text-statistics")
  implicit val materializer = ActorMaterializer()

  val fileName = args(0)

  val readFileSource = Source(() => scala.io.Source.fromFile(fileName).iter)

  val terminator = system.actorOf(Props(classOf[Terminator], statisticHandlers.size), "terminator")

  val graph = FlowGraph.closed() { implicit builder =>
    import FlowGraph.Implicits._

    val broadcast = builder.add(Broadcast[Char](statisticHandlers.size))

    readFileSource ~> broadcast
    for(s <- statisticHandlers) {
      broadcast ~> Sink.actorSubscriber(s)
    }
  }

  graph.run()



  def statisticHandlers = List(
    Props[PrintCharActor]
  )

}
