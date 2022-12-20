from rdflib import Graph, Namespace, URIRef
from rdflib.namespace import RDF, FOAF, XMLNS, OWL

import re
import random

import nltk
from nltk.corpus import wordnet

def test_wn():
    # print(wordnet.synsets('disjointWith'))
    # print(wordnet.synsets('disjointWith')[0].lemma_names())
    print(wordnet.synsets('fowl')[0].lemma_names())
    print(wordnet.synsets('fowl')[0].hypernyms()[0].lemma_names())
    print(wordnet.synsets('fowl')[0].part_meronyms()[0].lemma_names())

if __name__ == "__main__":
    test_wn()