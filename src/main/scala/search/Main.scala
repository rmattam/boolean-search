package search

object Main extends App {

  var input = "doc1 breakthrough  drug  for schizophrenia\nDoc2 new approach for treatment of schizophrenia\nDoc3 new hopes for schizophrenia patients\nDoc4 new schizophrenia drug"
  var index = new booleanIndex(input)
  index.build()
  println(index.docs)
  println(index.inverted)
  println(index.search("schizophrenia AND drug"))
  println(index.search("breakthrough OR new"))
  println(index.search("schizophrenia AND breakthrough OR new"))
  println(index.search("schizophrenia AND breakthrough AND new"))
  println(index.search("schizophrenia breakthrough new"))
}
