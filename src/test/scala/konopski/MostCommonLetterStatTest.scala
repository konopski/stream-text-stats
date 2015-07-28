package konopski

import org.scalatest.FunSuite

class MostCommonLetterStatTest extends FunSuite {

  test("not found when empty") {
    val stat = new MostCommon

    //when
    val mostCommon = stat.mostCommon()

    //then
    assert(None === mostCommon)
  }

  test("should found most frequent") {
    val stat = new MostCommon

    stat.count('a')
    stat.count('a')
    stat.count('b')

    //when
    val mostCommon = stat.mostCommon()

    //then
    assert(Some('a') === mostCommon)
  }

  test("should not count non letter") {
    val stat = new MostCommon

    stat.count('1')
    stat.count('1')
    stat.count('b')

    //when
    val mostCommon = stat.mostCommon()

    //then
    assert(Some('b') === mostCommon)
  }
}
