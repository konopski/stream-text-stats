# stream-text-stats
calculates stats on given text file

idea is that:
- stats are actor based so that we can easily add them
- reading file is balanced with processing speed thanks to akka-stream and backpressure

#requirements
you need sbt to run
http://www.scala-sbt.org/release/tutorial/Setup.html

# running tests
`sbt test`

#running stats
`sbt "run my-file"`
