package search

object Main extends App {

  var input = "doc1 breakthrough  drug  for schizophrenia\nDoc2 new approach for treatment of schizophrenia\nDoc3 new hopes for schizophrenia patients\nDoc4 new schizophrenia dr"
  var index = new booleanIndex(input)
  index.build()
  print(index.docs)
  print(index.inverted)
  print(index.docID)
}
