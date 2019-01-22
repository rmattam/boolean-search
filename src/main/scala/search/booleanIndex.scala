package search

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map

class booleanIndex(var docs: String) {

  var inverted = Map[String, ArrayBuffer[Int]]()
  var docID = Map[Int, String]()

  def build(): Unit = {
      var Id = 0
      for (lines <- docs.split('\n')) {
        val line = lines.split("[ ]+")
        Id += 1
        docID = docID + (Id -> line(0))
        for (i <- 1 until line.length){
          val token = line(i)
          if (!inverted.contains(token)) inverted.put(token, ArrayBuffer())
          inverted(token).append(Id)
        }
      }
  }
}
