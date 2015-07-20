package konopski

import java.text.DecimalFormat

import org.scalatest.FunSuite

class AvgCalculatorTest extends FunSuite {

  def fmt(d: Double) = new DecimalFormat("##.#").format(d)

  test("should return 0 when nothing was occured") {
    val avgCalculator = new AvgCalculator

    //when
    val average = avgCalculator.average

    //then
    assert(0.0 === average)

  }

  test("should count avg from some ints") {
    val avgCalculator = new AvgCalculator
    val given = List(1, 3, 4, 6, 1, 4)

    for(g <- given) avgCalculator.countOccurence(g)

    //when
    val average = avgCalculator.average

    //then
    assert(fmt(average) === "3.2")
  }

}
