similarity
==========

Calculate similarity between two stirng.
This is a simple example library which shows how to calulate jaccard index between two string.
Here is the simple api to follow :

Step 1 : Create a similarity calculator. This one uses Jaccard index to calculate similarity between two strings.
SimilarityCalculator calculator = new JaccardIndexBasedSimilarity();

Step 2: Calulate similarity between two strings
calculator.calculateSimilarity("Mary had a little lamb", "The lamb was sure to go.")
