# Search Engine
This program is SearchEngine
To achieve all the basic functions:
1. inverted index
2. Vector space model
3. Support score
4. sorting
5.TKK query.

Also implemented some additional features:
1. Boolean query (C++)
2. Index Compression (C++)
3. Correction (the introduction of foreign thesaurus, check the user's spelling errors, before the query to the user to ask whether the use of correct input)
4. Regularization of the word (the word normalized operation, such as the third person singular, past, gerund and other special forms of the word normalized into the word prototype)
5. Optimize the vector space (using hashMap to preserve the simple Tf for the existence of the term, reduce the time and space complexity of the algorithm, and restore the tf-idf weight vector w in the calculation to ensure the accuracy of the query)

Instructions:
1. Run main.java You can set the scope of the query document set (default is 20000) and the location in ReadFiles.
2. Generate an inverted index
3. Enter the query's sentence (enter quit to exit)
4. return the spell to correct the sentence (yes for the confirmation, no to give up, use the original input query)
5. Output TopK sort (K defaults to 6)
6. Cycle calculation can be re-inquired.

Boolean query: (added)


SearchEngine for the entire project file
src: SearchEngine source code package
     Reuters for the document set
     Test for the test document set
     big.txt for dictionary for spelling correction
