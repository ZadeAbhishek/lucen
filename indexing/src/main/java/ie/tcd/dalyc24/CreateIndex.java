package ie.tcd.dalyc24;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class CreateIndex {
    // Directory where the search index will be saved
    private static final String INDEX_DIRECTORY = "../index";

    public static void main(String[] args) throws IOException {
        // Make sure we were given something to index
        if (args.length <= 0) {
            System.out.println("Expected corpus as input");
            System.exit(1);
        }

        // Analyzer that is used to process TextField
        Analyzer analyzer = new StandardAnalyzer();

        // Open the directory that contains the search index
        Directory directory = FSDirectory.open(Paths.get(INDEX_DIRECTORY));

        // Set up an index writer to add process and save documents to the index
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter iwriter = new IndexWriter(directory, config);

        // Read and parse the Cranfield document collection
        String cranfieldFile = args[0];
        List<Document> documents = parseCranfieldFile(cranfieldFile);

        // Write all the documents to the search index
        iwriter.addDocuments(documents);

        // Commit everything and close
        iwriter.close();
        directory.close();
    }

    // Method to parse the Cranfield collection file
    private static List<Document> parseCranfieldFile(String filePath) throws IOException {
        List<Document> documents = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        Document currentDoc = null;
        StringBuilder contentBuilder = new StringBuilder();
        String currentField = "";

    for (String line : lines) {
        line = line.trim();

        if (line.startsWith(".I")) {
            // Save the previous document before starting a new one
            if (currentDoc != null) {
                // Add the accumulated content to the appropriate field
                if (contentBuilder.length() > 0 && !currentField.isEmpty()) {
                    currentDoc.add(new TextField(currentField, contentBuilder.toString(), Field.Store.YES));
                }
                documents.add(currentDoc);
            }
            // Create a new document
            currentDoc = new Document();
            contentBuilder.setLength(0); // Clear the content builder

            String docId = line.substring(3).trim();
            currentDoc.add(new StringField("docID", docId, Field.Store.YES));
            currentField = "";
        } else if (line.startsWith(".T")) {
            // Save previous field content if it exists
            if (currentField.equals("content")) {
                currentDoc.add(new TextField("content", contentBuilder.toString(), Field.Store.YES));
            }
            // Start a new field
            currentField = "title";
            contentBuilder.setLength(0);
        } else if (line.startsWith(".A")) {
            if (!currentField.isEmpty()) {
                currentDoc.add(new TextField(currentField, contentBuilder.toString(), Field.Store.YES));
            }
            currentField = "author";
            contentBuilder.setLength(0);
        } else if (line.startsWith(".B")) {
            if (!currentField.isEmpty()) {
                currentDoc.add(new TextField(currentField, contentBuilder.toString(), Field.Store.YES));
            }
            currentField = "bibliography";
            contentBuilder.setLength(0);
        } else if (line.startsWith(".W")) {
            if (!currentField.isEmpty()) {
                currentDoc.add(new TextField(currentField, contentBuilder.toString(), Field.Store.YES));
            }
            currentField = "content";
            contentBuilder.setLength(0);
        } else {
            // Accumulate the content for the current field
            if (contentBuilder.length() > 0) {
                contentBuilder.append(" ");
            }
            contentBuilder.append(line);
        }
    }

    // Add the last document
    if (currentDoc != null) {
        if (!currentField.isEmpty() && contentBuilder.length() > 0) {
            currentDoc.add(new TextField(currentField, contentBuilder.toString(), Field.Store.YES));
        }
        documents.add(currentDoc);
    }

    // For demonstration purposes, print the documents (you would index them using Lucene)
    for (Document doc : documents) {
        System.out.println("Document ID: " + doc.get("docID"));
        System.out.println("Title: " + doc.get("title"));
        System.out.println("Author: " + doc.get("author"));
        System.out.println("Bibliography: " + doc.get("bibliography"));
        System.out.println("Content: " + doc.get("content"));
        System.out.println("----------------------------------");
    }

        return documents;
    }
}