package konopski

import akka.actor.{ActorRef, Props, ActorSystem}
import akka.testkit.{TestProbe, ImplicitSender, TestKit}
import konopski.WordLen.Len
import org.scalatest.{BeforeAndAfter, FunSuite}

class WordLenTest extends FunSuite with BeforeAndAfter {
  implicit var system: ActorSystem = null
  var wordLen: ActorRef = null
  var p: TestProbe = null

  before {
    system = ActorSystem("test-system")
    wordLen = system.actorOf(Props(classOf[WordLen], false))
    p = TestProbe()
  }

  after {
    system.shutdown()
  }

  test("should respond with 0 to space") {
    val givenChar: Char = ' '
    //when
    p.send(wordLen, givenChar)
    //then
    p.expectMsg(Len(0))
  }

  test("should respond with 0 to newline") {
    val givenChar: Char = '\n'
    
    //when
    p.send(wordLen, givenChar)
    //then
    p.expectMsg(Len(0))
  }

  test("should respond with 0 to tab") {
    val givenChar: Char = '\t'
    
    //when
    p.send(wordLen, givenChar)
    //then
    p.expectMsg(Len(0))
  }

  test("should not respond to A") {
    val givenChar: Char = 'A'

    //when
    p.send(wordLen, givenChar)
    //then
    p.expectNoMsg()
  }

  test("should respond with 1 to A<space>") {
    val givenChars = "A "

    //when
    for(givenChar<- givenChars)
      p.send(wordLen, givenChar)
    //then
    p.expectMsg(Len(1))
  }

  test("should not respond to ABC") {
    val givenChars = "ABC"
    val expectedResp = 3

    //when
    for(givenChar <- givenChars)
      p.send(wordLen, givenChar)

    //then
    p.expectNoMsg()
  }

  test("should respond with 3 to ABC<space>") {
    val givenChars = "ABC "
    val expectedResp = 3

    //when
    for(givenChar <- givenChars)
      p.send(wordLen, givenChar)

    //then
    p.expectMsg(Len(3))
  }

  test("should respond with 0 to 3 spaces") {
    val givenChars = "   "
    val expectedResp = 0

    //when
    for(givenChar <- givenChars) {
      p.send(wordLen, givenChar)

      //then
      p.expectMsg(Len(0))
    }
  }

  test("should respond with 3 to word containing not only letters") {
    val givenChars = "A2B5!5[C "
    val expectedResp = 3

    //when
    val wordLenJustLetters = system.actorOf(Props(classOf[WordLen], true))
    for(givenChar <- givenChars)
      p.send(wordLenJustLetters, givenChar)

    //then
    p.expectMsg(Len(3))
  }

}
