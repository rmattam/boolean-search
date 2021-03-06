package search

import Control._

object Main extends App{

  if (args.length == 0) {
    println("Missing commandline argument: <filename/location>. Please enter the filename of plain text file having new line delimited documents to be indexed!")
  } else {
    var index = new booleanIndex()
    using(io.Source.fromFile(args(0))) { source => {
      for (line <- source.getLines) {
        index.add(line)
      }
    }
    }

    println("Documents have been Indexed Correctly!")
    println("Enter your search query after the prompt. Type exit/quit to stop")

    var query: String = ""
    while (query != "exit" && query != "quit") {
      query = scala.io.StdIn.readLine(">> ")
      println(index.search(query))
    }
  }
}

object Control {
  def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B =
    try {
      f(resource)
    } finally {
      resource.close()
    }
}
