# Instruction to run this repository

1. mvn clean compile
2. mvn package
3. java -jar indexing-1.jar ../cran.all.1400

1. java -cp target/interactive-querying-1.jar ie.tcd.dalyc24.QueryIndex ../cran.qry ../result.txt

1.  ./trec_eval ../converted_cranqrel.txt ../result.txt

