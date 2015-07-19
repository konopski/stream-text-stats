package konopski

class LineCountStat extends BaseStat {

  var linesCounter = 0

  override def nextChar(ch: Char) = if('\n' == ch) linesCounter += 1

  override def collectedStats = "Number of lines: " + linesCounter
}
