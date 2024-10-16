input_file = 'cranqrel'
output_file = 'converted_cranqrel.txt'

with open(input_file, 'r') as infile, open(output_file, 'w') as outfile:
    for line in infile:
        parts = line.strip().split()
        if len(parts) == 3:
            query_id = parts[0]
            doc_id = parts[1]
            relevance = parts[2]
            # Write in TREC qrels format: query_id iteration doc_id relevance
            outfile.write(f"{query_id} 0 {doc_id} {relevance}\n")