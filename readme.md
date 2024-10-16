# Instructions to Run the Project

## 1. Build the Project

First, clean and compile the project using Maven:

```bash
mvn clean compile
```

Then package the project:

```bash
mvn package
```

## 2. Run Indexing

To run the indexing, use the following command:

```bash
java -jar indexing-1.jar ../cran.all.1400
```

## 3. Run Querying

You can run the querying module with different tokenization approaches. Here are the commands for each approach:

- **Whitespace Tokenization:**

    ```bash
    java -jar target/querying-1.jar ../cran.qry ../result_whitespace.txt whitespace
    ```

- **Standard Tokenization:**

    ```bash
    java -jar target/querying-1.jar ../cran.qry ../result_standard.txt standard
    ```

- **Simple Tokenization:**

    ```bash
    java -jar target/querying-1.jar ../cran.qry ../result_simple.txt simple
    ```

## 4. Evaluate Results

To evaluate the results using `trec_eval`, run the following commands for each result file:

- **Evaluate Simple Tokenization:**

    ```bash
    ./trec_eval ../converted_cranqrel.txt ../result_simple.txt
    ```

- **Evaluate Standard Tokenization:**

    ```bash
    ./trec_eval ../converted_cranqrel.txt ../result_standard.txt
    ```

- **Evaluate Whitespace Tokenization:**

    ```bash
    ./trec_eval ../converted_cranqrel.txt ../result_whitespace.txt
    ```

## 5. Final Evaluation Metrics

Here are the updated final results for all three tokenization methods, including all precision metrics based on the latest evaluation:

---

### **Simple Tokenization**

| Metric                    | Value    |
|----------------------------|----------|
| num_q                      | 225      |
| num_ret                    | 11250    |
| num_rel                    | 1612     |
| num_rel_ret                | 860      |
| MAP                        | 0.2531   |
| GM_MAP                     | 0.0827   |
| Rprec                      | 0.2698   |
| Bpref                      | 0.5786   |
| Reciprocal Rank            | 0.5082   |
| Precision @ 5              | 0.2898   |
| Precision @ 10             | 0.2084   |
| Precision @ 15             | 0.1639   |
| Precision @ 20             | 0.1391   |
| Precision @ 30             | 0.1077   |
| Precision @ 100            | 0.0382   |
| Precision @ 200            | 0.0191   |
| Precision @ 500            | 0.0076   |
| Precision @ 1000           | 0.0038   |

---

### **Standard Tokenization**

| Metric                    | Value    |
|----------------------------|----------|
| num_q                      | 225      |
| num_ret                    | 11250    |
| num_rel                    | 1612     |
| num_rel_ret                | 859      |
| MAP                        | 0.2523   |
| GM_MAP                     | 0.0850   |
| Rprec                      | 0.2717   |
| Bpref                      | 0.5812   |
| Reciprocal Rank            | 0.5026   |
| Precision @ 5              | 0.2889   |
| Precision @ 10             | 0.2076   |
| Precision @ 15             | 0.1659   |
| Precision @ 20             | 0.1378   |
| Precision @ 30             | 0.1077   |
| Precision @ 100            | 0.0382   |
| Precision @ 200            | 0.0191   |
| Precision @ 500            | 0.0076   |
| Precision @ 1000           | 0.0038   |

---

### **Whitespace Tokenization**

| Metric                    | Value    |
|----------------------------|----------|
| num_q                      | 225      |
| num_ret                    | 11250    |
| num_rel                    | 1612     |
| num_rel_ret                | 786      |
| MAP                        | 0.2235   |
| GM_MAP                     | 0.0639   |
| Rprec                      | 0.2381   |
| Bpref                      | 0.5374   |
| Reciprocal Rank            | 0.4925   |
| Precision @ 5              | 0.2622   |
| Precision @ 10             | 0.1818   |
| Precision @ 15             | 0.1493   |
| Precision @ 20             | 0.1273   |
| Precision @ 30             | 0.0994   |
| Precision @ 100            | 0.0349   |
| Precision @ 200            | 0.0175   |
| Precision @ 500            | 0.0070   |
| Precision @ 1000           | 0.0035   |

---