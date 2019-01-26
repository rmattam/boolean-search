package search

import collection.mutable.{ArrayBuffer, Stack}
import org.scalatest._

class ExampleSpec extends FlatSpec with Matchers {

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    stack.pop() should be (2)
    stack.pop() should be (1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[Int]
    a [NoSuchElementException] should be thrownBy {
      emptyStack.pop()
    }
  }
}

class SearchSpec extends FlatSpec with Matchers {
  val input = "Doc1 breakthrough  drug  for schizophrenia\nDoc2 new approach for treatment of schizophrenia\nDoc3 new hopes for schizophrenia patients\nDoc4 new schizophrenia drug"
  val index = new booleanIndex(input)
  index.build()
  "A AND Search" should "return the intersection" in {
    index.search("schizophrenia AND drug") should equal (ArrayBuffer[String]("Doc1", "Doc4"))
  }
}