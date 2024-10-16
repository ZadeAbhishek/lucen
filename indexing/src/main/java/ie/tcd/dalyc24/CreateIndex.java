package ie.tcd.dalyc24;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.similarities.ClassicSimilarity;

public class CreateIndex {
    private static final String INDEX_DIRECTORY = "../index";

    public static void main(String[] args) throws IOException {
        if (args.length <= 0) {
            System.out.println("Expected corpus as input");
            System.exit(1);
        }

        String cranfieldFile = args[0];

        // Define analyzers
        Analyzer[] analyzers = {
            new StandardAnalyzer(),
            new SimpleAnalyzer(),
            new WhitespaceAnalyzer()
        };

        // Define index directory names corresponding to each analyzer
        String[] indexDirs = {
            INDEX_DIRECTORY + "_standard",
            INDEX_DIRECTORY + "_simple",
            INDEX_DIRECTORY + "_whitespace"
        };

        for (int i = 0; i < analyzers.length; i++) {
            createIndex(cranfieldFile, analyzers[i], indexDirs[i]);
        }
    }

    private static void createIndex(String cranfieldFile, Analyzer analyzer, String indexDir) throws IOException {
        Directory directory = FSDirectory.open(Paths.get(indexDir));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setSimilarity(new ClassicSimilarity()); // Set TF-IDF similarity
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter iwriter = new IndexWriter(directory, config);

        List<Document> documents = parseCranfieldFile(cranfieldFile);
        iwriter.addDocuments(documents);

        iwriter.close();
        directory.close();
    }

    private static List<Document> parseCranfieldFile(String filePath) throws IOException {
        List<Document> documents = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        Document currentDoc = null;
        StringBuilder contentBuilder = new StringBuilder();
        String currentField = "";

        for (String line : lines) {
            line = line.trim();

            if (line.startsWith(".I")) {
                if (currentDoc != null) {
                    if (contentBuilder.length() > 0 && !currentField.isEmpty()) {
                        currentDoc.add(new TextField(currentField, contentBuilder.toString(), Field.Store.YES));
                    }
                    documents.add(currentDoc);
                }

                currentDoc = new Document();
                contentBuilder.setLength(0);

                String docId = line.substring(3).trim();
                currentDoc.add(new StringField("docID", docId, Field.Store.YES));
                currentField = "";
            } else if (line.startsWith(".T")) {
                if (currentField.equals("content")) {
                    currentDoc.add(new TextField("content", contentBuilder.toString(), Field.Store.YES));
                }
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
                if (contentBuilder.length() > 0) {
                    contentBuilder.append(" ");
                }
                contentBuilder.append(line);
            }
        }

        if (currentDoc != null) {
            if (!currentField.isEmpty() && contentBuilder.length() > 0) {
                currentDoc.add(new TextField(currentField, contentBuilder.toString(), Field.Store.YES));
            }
            documents.add(currentDoc);
        }

        return documents;
    }
}