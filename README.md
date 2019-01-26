# boolean-search

A scala app to perform boolean search on an inverted index from a file containing a new line separated list of documents.
The first token in each line of the input file is interpreted as the user specified document id and is not searchable.

### Instructions

The project uses:
sbt.version = 1.2.8
scalaVersion := "2.12.8"

Clone this repository and run sbt run to compile and spawn up an instance.

Run the sbt run Main command with the command line argument mentioning the file.txt that needs to be indexed.

```
sbt 'runMain search.Main documents/index.txt'
```

On seeing the message that the documents are indexed correctly, type your query after the prompt.

```
Documents have been Indexed Correctly!
Enter your search query after the prompt. Type exit/quit to stop
>> schizophrenia AND drug
ArrayBuffer(Doc1, Doc4)
```

### General assumptions
1. The code assumes that the input documents to be added to the inverted index and the search query are easily tokenizable and normalized correctly.
The code uses a simple white space regex to extract all tokens from input documents and query string.
2. While searching, the upper-case 'AND' token is parsed as a conjunction and upper-case 'OR' token is parsed as a disjunction operation.
All Tokens which do not match the two mentioned tokens 'AND' / 'OR' are considered part of terms to be searched.
3. The 'AND' conjunction operation has a higher precedence compared to the 'OR' token for better performance. In case of tie breaks, the query string is parsed in order of left to right.
4. Parenthesis in the query string are not supported.

### Testing

Run sbt test to run the test cases created.

```
sbt test
```
