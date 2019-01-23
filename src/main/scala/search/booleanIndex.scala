package search

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map

class booleanQuery(val query: String, val docs: ArrayBuffer[Int], val original: Boolean)

class booleanIndex(var docs: String) {

  var inverted = Map[String, ArrayBuffer[Int]]().withDefaultValue(ArrayBuffer[Int]())
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

  /**
    * Search function assumes that the input query uses AND / OR keywords in upper case, case of terms are not modified.
    * @param query
    */
  def search(input: String): ArrayBuffer[String] ={

    def conjunction(term1: booleanQuery, term2: booleanQuery): booleanQuery = {
      val result = ArrayBuffer[Int]()
      var i,j = 0
      while (i < term1.docs.length && j < term2.docs.length){
          if(term1.docs(i) == term2.docs(j)){
            result.append(term1.docs(i))
            i+=1
            j+=1
          } else if (term1.docs(i) < term2.docs(j)){
            i+=1
          } else {
            j+=1
          }
      }
      return new booleanQuery(term1.query + " AND " + term2.query, result, false)
    }

    def disjunction(term1: booleanQuery, term2: booleanQuery): booleanQuery = {
      val result = ArrayBuffer[Int]()
      var i,j = 0
      while (i < term1.docs.length && j < term2.docs.length){
        if(term1.docs(i) == term2.docs(j)){
          result.append(term1.docs(i))
          i+=1
          j+=1
        } else if (term1.docs(i) < term2.docs(j)){
          result.append(term1.docs(i))
          i+=1
        } else {
          result.append(term2.docs(j))
          j+=1
        }
      }

      while (i < term1.docs.length) {
        result.append(term1.docs(i))
        i+=1
      }

      while (j < term2.docs.length) {
        result.append(term2.docs(j))
        j+=1
      }

      return new booleanQuery(term1.query + " OR " + term2.query, result, false)
    }

    def apply_AND(tokens:ArrayBuffer[booleanQuery]):ArrayBuffer[booleanQuery] ={
      var i = 0
      val resolved = ArrayBuffer[booleanQuery]()
      while (i+2 < tokens.length && tokens(i+1).query != "AND"){
        resolved.append(tokens(i))
        i+=1
      }

      resolved.append(conjunction(tokens(i), tokens(i+2)))
      i+=3

      while(i < tokens.length){
        resolved.append(tokens(i))
        i+=1
      }

      return resolved
    }

    def apply_OR(tokens:ArrayBuffer[booleanQuery]):ArrayBuffer[booleanQuery] ={
      var i = 0
      val resolved = ArrayBuffer[booleanQuery]()
      while (i+2 < tokens.length && tokens(i+1).query != "OR"){
        resolved.append(tokens(i))
        i+=1
      }

      resolved.append(disjunction(tokens(i), tokens(i+2)))
      i+=3

      while(i < tokens.length){
        resolved.append(tokens(i))
        i+=1
      }

      return resolved
    }


    var query = ArrayBuffer[booleanQuery]()
    var valid = false
    for(token:String <- input.split("[ ]+")){
      query.append(new booleanQuery(token, inverted(token), true))
      if (token == "AND" || token == "OR") valid = true
    }

    if (!valid) return ArrayBuffer[String]("Invalid query")

    while (query.length > 3){
      if (query.map(_.query).contains("AND")){
        query = apply_AND(query)
      } else if (query.map(_.query).contains("OR")){
        query = apply_OR(query)
      }
    }

    if (query.map(_.query).contains("AND")) {
      query = ArrayBuffer[booleanQuery](conjunction(query(0), query(2)))
    } else {
      query = ArrayBuffer[booleanQuery](disjunction(query(0), query(2)))
    }
    return query(0).docs.map(docID(_))
  }
}
