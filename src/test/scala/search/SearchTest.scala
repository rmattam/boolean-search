package search

import collection.mutable.{ArrayBuffer, Stack}
import org.scalatest._


class SearchSpec extends FlatSpec with Matchers {
  val input = "Doc1 breakthrough  drug  for schizophrenia\nDoc2 new approach for treatment of schizophrenia\nDoc3 new hopes for schizophrenia patients\nDoc4 new schizophrenia drug"
  val index = new booleanIndex()
  index.build_from_string(input)

  "A Boolean Search Index" should "return the simple intersection for AND query 1" in {
    index.search("schizophrenia AND drug") should equal (ArrayBuffer[String]("Doc1", "Doc4"))
  }

  it should "return the simple intersection for AND query 2" in {
    index.search("breakthrough AND drug") should equal (ArrayBuffer[String]("Doc1"))
  }

  it should "return the simple intersection for AND query 3" in {
    index.search("hopes AND schizophrenia") should equal (ArrayBuffer[String]("Doc3"))
  }

  it should "return the simple disjunction for OR query 1" in {
    index.search("breakthrough OR drug") should equal (ArrayBuffer[String]("Doc1", "Doc4"))
  }

  it should "return the simple disjunction for OR query 2" in {
    index.search("breakthrough OR new") should equal (ArrayBuffer[String]("Doc1", "Doc2", "Doc3", "Doc4"))
  }

  it should "return the simple disjunction for OR query 3" in {
    index.search("patients OR drug") should equal (ArrayBuffer[String]("Doc1", "Doc3", "Doc4"))
  }

  it should "return the complex query result for AND OR query 1" in {
    index.search("schizophrenia AND breakthrough OR new") should equal (ArrayBuffer[String]("Doc1", "Doc2", "Doc3", "Doc4"))
  }

  it should "return the complex query result for AND OR query 2" in {
    index.search("schizophrenia AND breakthrough AND new") should equal (ArrayBuffer[String]())
  }

  it should "return the complex query result for AND OR query 3" in {
    index.search("hopes OR breakthrough OR new AND drug") should equal (ArrayBuffer[String]("Doc1", "Doc3", "Doc4"))
  }

  it should "return the complex query result for AND OR query 4" in {
    index.search("hopes OR breakthrough OR new OR drug") should equal (ArrayBuffer[String]("Doc1", "Doc2", "Doc3", "Doc4"))
  }

  it should "return the complex query result for AND OR query 5" in {
    index.search("schizophrenia AND patients AND for") should equal (ArrayBuffer[String]("Doc3"))
  }


  it should "return the error message for the invalid query" in {
    index.search("schizophrenia breakthrough new") should equal (ArrayBuffer[String]("Invalid query"))
  }
}