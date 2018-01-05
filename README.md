
#Word Frequency Counter
Word frequency counter for local HTML pages and web pages.

Project was created for Introduction to Information Structures taught by Jordan Kidney.

##Input/Output
Input file is a txt file with the list of HTML files/webpages. Before the link an F or W is put to denote local file (F) or web page (W).

The user is then prompted for an output file name. 

Two .csv files are output: 
1. A list of the frequency of each word
2. A list of where each word was found.

##Implementation
Word frequency was implemented by parsing the HTML tree with jsoup and putting each word into a Binary Search Tree. Each node is a word object in order to keep track of word location.
