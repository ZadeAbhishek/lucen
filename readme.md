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

#### For preprocessing we are using  **Apache OpenNLP**
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

Here are the updated tables for **Simple**, **Standard**, and **Whitespace** tokenization models based on the latest evaluation results:

---

| Metric                    | Value    |
|----------------------------|----------|
| num_q                      | 225      |
| num_ret                    | 11250    |
| num_rel                    | 1612     |
| num_rel_ret                | 857      |
| MAP                        | 0.2410   |
| GM_MAP                     | 0.0820   |
| Rprec                      | 0.2585   |
| Bpref                      | 0.5813   |
| Reciprocal Rank            | 0.4817   |
| Precision @ 5              | 0.2738   |
| Precision @ 10             | 0.1996   |
| Precision @ 15             | 0.1639   |
| Precision @ 20             | 0.1380   |
| Precision @ 30             | 0.1079   |
| Precision @ 100            | 0.0381   |
| Precision @ 200            | 0.0190   |
| Precision @ 500            | 0.0076   |
| Precision @ 1000           | 0.0038   |

---

### **Standard Tokenization**

| Metric                    | Value    |
|----------------------------|----------|
| num_q                      | 225      |
| num_ret                    | 11250    |
| num_rel                    | 1612     |
| num_rel_ret                | 860      |
| MAP                        | 0.2377   |
| GM_MAP                     | 0.0816   |
| Rprec                      | 0.2590   |
| Bpref                      | 0.5828   |
| Reciprocal Rank            | 0.4695   |
| Precision @ 5              | 0.2702   |
| Precision @ 10             | 0.1991   |
| Precision @ 15             | 0.1636   |
| Precision @ 20             | 0.1378   |
| Precision @ 30             | 0.1083   |
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
| num_rel_ret                | 792      |
| MAP                        | 0.2287   |
| GM_MAP                     | 0.0622   |
| Rprec                      | 0.2433   |
| Bpref                      | 0.5441   |
| Reciprocal Rank            | 0.4815   |
| Precision @ 5              | 0.2658   |
| Precision @ 10             | 0.1867   |
| Precision @ 15             | 0.1538   |
| Precision @ 20             | 0.1300   |
| Precision @ 30             | 0.1004   |
| Precision @ 100            | 0.0352   |
| Precision @ 200            | 0.0176   |
| Precision @ 500            | 0.0070   |
| Precision @ 1000           | 0.0035   |

---