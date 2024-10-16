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

### **Simple Tokenization**

| Metric                    | Value    |
|----------------------------|----------|
| num_q                      | 225      |
| num_ret                    | 11250    |
| num_rel                    | 1612     |
| num_rel_ret                | 863      |
| MAP                        | 0.2543   |
| GM_MAP                     | 0.0947   |
| Rprec                      | 0.2782   |
| Bpref                      | 0.5897   |
| Reciprocal Rank            | 0.5032   |
| Precision @ 5              | 0.2916   |
| Precision @ 10             | 0.2169   |
| Precision @ 15             | 0.1721   |
| Precision @ 20             | 0.1453   |
| Precision @ 30             | 0.1108   |
| Precision @ 100            | 0.0384   |
| Precision @ 200            | 0.0192   |
| Precision @ 500            | 0.0077   |
| Precision @ 1000           | 0.0038   |

---

### **Standard Tokenization**

| Metric                    | Value    |
|----------------------------|----------|
| num_q                      | 225      |
| num_ret                    | 11250    |
| num_rel                    | 1612     |
| num_rel_ret                | 864      |
| MAP                        | 0.2539   |
| GM_MAP                     | 0.0953   |
| Rprec                      | 0.2766   |
| Bpref                      | 0.5901   |
| Reciprocal Rank            | 0.5016   |
| Precision @ 5              | 0.2942   |
| Precision @ 10             | 0.2164   |
| Precision @ 15             | 0.1721   |
| Precision @ 20             | 0.1453   |
| Precision @ 30             | 0.1111   |
| Precision @ 100            | 0.0384   |
| Precision @ 200            | 0.0192   |
| Precision @ 500            | 0.0077   |
| Precision @ 1000           | 0.0038   |

---

### **Whitespace Tokenization**

| Metric                    | Value    |
|----------------------------|----------|
| num_q                      | 225      |
| num_ret                    | 11250    |
| num_rel                    | 1612     |
| num_rel_ret                | 814      |
| MAP                        | 0.2296   |
| GM_MAP                     | 0.0676   |
| Rprec                      | 0.2448   |
| Bpref                      | 0.5521   |
| Reciprocal Rank            | 0.4929   |
| Precision @ 5              | 0.2676   |
| Precision @ 10             | 0.1947   |
| Precision @ 15             | 0.1532   |
| Precision @ 20             | 0.1342   |
| Precision @ 30             | 0.1033   |
| Precision @ 100            | 0.0362   |
| Precision @ 200            | 0.0181   |
| Precision @ 500            | 0.0072   |
| Precision @ 1000           | 0.0036   |

---