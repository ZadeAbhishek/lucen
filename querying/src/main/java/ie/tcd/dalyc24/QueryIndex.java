package ie.tcd.dalyc24;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.queryparser.classic.QueryParser;

public class QueryIndex {

    // Directory where the existing index is saved
    private static final String INDEX_DIRECTORY = "../index";

    private Analyzer analyzer;
    private Directory directory;

    public QueryIndex() throws IOException {
        // Initialize the analyzer and directory for the existing index
        this.analyzer = new StandardAnalyzer();
        this.directory = FSDirectory.open(Paths.get(INDEX_DIRECTORY));
    }

    // Method to read queries from cran.qry file
    private List<String> readQueries(String queryFilePath) throws IOException {
        List<String> queries = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(queryFilePath));

        StringBuilder queryBuilder = new StringBuilder();
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith(".I")) {
                if (queryBuilder.length() > 0) {
                    queries.add(queryBuilder.toString().trim());
                }
                queryBuilder.setLength(0); // Clear for the next query
            } else if (line.startsWith(".W")) {
                queryBuilder.setLength(0); // Clear previous content in preparation for new query
            } else {
                queryBuilder.append(line).append(" ");
            }
        }
        if (queryBuilder.length() > 0) {
            queries.add(queryBuilder.toString().trim());
        }

        return queries;
    }

    // Method to print all documents in the index
    public void printAllDocuments() throws IOException {
        DirectoryReader ireader = DirectoryReader.open(directory);
        System.out.println("Printing all documents in the index...");

        for (int i = 0; i < ireader.maxDoc(); i++) {
            Document doc = ireader.document(i);
            System.out.println("Document ID: " + doc.get("docID"));
            System.out.println("Filename: " + doc.get("filename"));
            System.out.println("Title: " + doc.get("title"));
            System.out.println("Author: " + doc.get("author"));
            System.out.println("Bibliography: " + doc.get("bibliography"));
            System.out.println("Content: " + doc.get("content"));
            System.out.println("----------------------------------");
        }

        ireader.close();
    }

    public void queryIndex(String queryFilePath, String resultsFilePath) throws IOException {
        // Read the queries from the specified file
        List<String> queries = readQueries(queryFilePath);

        // Open the existing index directory
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);

        // List to hold all result lines
        List<String> results = new ArrayList<>();

        // Iterate over each query in the query file
        for (int i = 0; i < queries.size(); i++) {
            String queryText = queries.get(i);
            System.out.printf("Processing Query %d: %s\n", i + 1, queryText);

            try {
                // Use QueryParser to parse the query text
                QueryParser parser = new QueryParser("content", analyzer);
                Query query = parser.parse(QueryParser.escape(queryText));

                // Retrieve the top 50 documents for each query
                TopDocs topDocs = isearcher.search(query, 50);

                // Prepare the results in TREC format
                for (int rank = 0; rank < topDocs.scoreDocs.length; rank++) {
                    ScoreDoc scoreDoc = topDocs.scoreDocs[rank];
                    Document doc = isearcher.doc(scoreDoc.doc);

                    // Get document identifier (using either docID or filename)
                    String docId = doc.get("docID");
                    if (docId == null) {
                        docId = doc.get("filename"); // Fallback if 'docID' is not available
                    }

                    float score = scoreDoc.score;

                    // TREC format: query_id Q0 document_id rank score STANDARD
                    String resultLine = String.format("%d Q0 %s %d %f STANDARD", (i + 1), docId, (rank + 1), score);
                    results.add(resultLine);
                }
            } catch (Exception e) {
                System.out.printf("Error processing query %d: %s\n", i + 1, e.getMessage());
            }
        }

        // Write all results to the output file at once
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultsFilePath))) {
            for (String result : results) {
                writer.write(result);
                writer.newLine();
            }
        }

        // Close the reader
        ireader.close();
    }

    public void shutdown() throws IOException {
        directory.close();
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java QueryIndex <query file> <results file>");
            System.exit(1);
        }

        String queryFile = args[0];
        String resultsFile = args[1];

        // Create an instance of QueryIndex
        QueryIndex qi = new QueryIndex();

        // Print all documents before querying
        qi.printAllDocuments();

        // Use the instance to perform the queries
        qi.queryIndex(queryFile, resultsFile);

        // Shutdown the index
        qi.shutdown();
    }
}