{\rtf1\ansi\ansicpg1252\cocoartf1504\cocoasubrtf820
{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\margl1440\margr1440\vieww10800\viewh8400\viewkind0
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\f0\fs24 \cf0 #Word Frequency Counter\
Word frequency counter for local HTML pages and web pages.\
\
Project was created for Introduction to Information Structures taught by Jordan Kidney.\
\
##Input/Output\
Input file is a txt file with the list of HTML files/webpages. Before the link an F or W is put to denote local file (F) or web page (W).\
\
The user is then prompted for an output file name. \
\
Two .csv files are output: \
1. A list of the frequency of each word\
2. A list of where each word was found.\
\
##Implementation\
Word frequency was implemented by parsing the HTML tree with jsoup and putting each word into a Binary Search Tree. Each node is a word object in order to keep track of word location.}