import akka.actor.{Props, ActorSystem}
import akka.stream._
import akka.stream.scaladsl._

/** solution for Task 20150701 */
object TextStatisticsApp extends App {

  implicit val system = ActorSystem("text-statistics")
  implicit val materializer = ActorMaterializer()

  val fileName = args(0)

  val getLines = () => scala.io.Source.fromFile(fileName).iter

//  val readFileSource = Source(getLines).map { line => ByteString(line + "\n") }
  val readFileSource = Source(getLines)

  val doneFileSink = Sink.onComplete { r =>
    println("File " + fileName + " done with: " + r)
    system.shutdown()
  }


  val graph = FlowGraph.closed() { implicit b =>
    import FlowGraph.Implicits._

//    val broadcast = b.add(Broadcast[ByteString](statisticHandlers.size))
    val broadcast = b.add(Broadcast[Char](statisticHandlers.size + 1))
    readFileSource ~> broadcast

    for(s <- statisticHandlers) {
//      readFileSource ~> broadcast ~> doneFileSink
      broadcast ~> Sink.actorSubscriber(s)

    }
    broadcast ~> doneFileSink
  }

  graph.run()


  def statisticHandlers = List(
    Props[PrintCharActor]
  )

}
