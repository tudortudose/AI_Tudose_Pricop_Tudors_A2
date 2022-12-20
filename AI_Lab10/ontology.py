from rdflib import Graph, Namespace, URIRef
from rdflib.namespace import RDF, FOAF, XMLNS, OWL

import re
import random

import nltk
from nltk.corpus import wordnet

############## RDF ontology ##############

## 2)
def print_ontology_triplets(input_file='food.rdf', output_file='food.txt'):
    graph = Graph()
    graph.parse(input_file)

    with open(output_file, 'w') as of:
        for subject, predicate, object in graph.triples((None, None, None)):
            # subject = subject.split("/")[-1]
            # predicate = predicate.split("/")[-1]
            # object = object.split("/")[-1]
            try:
                subject = graph.compute_qname(subject)[2]
                predicate = graph.compute_qname(predicate)[2]
                object = graph.compute_qname(object)[2]
            except ValueError:
                continue
            of.write(f'{subject} {predicate} {object}\n')

## 3)
def question_ontology(ontology_file='food.txt'):
    relations = []
    with open(ontology_file, 'r') as of:
        for line in of.readlines():
            relations += [tuple(re.split(" ", line.strip()))]
    
    while True:
        print('Press q to stop or other key to continue...')

        key = input()
        if key.startswith('q'):
            print('Program ending...')
            break

        relation = relations[int(random.random()*(len(relations)-1))]
        elem_missing = int(random.random()*2)
        if elem_missing == 1:
            print(f'What is the relationship between {relation[0]} and {relation[2]}?')
        elif elem_missing == 0:
            print(f'Who is in relationship {relation[1]} with {relation[2]}?')
        elif elem_missing == 2:
            print(f'{relation[0]} is in relationship {relation[1]} with whom?')

        answer = input().strip()
        if answer.lower() == relation[elem_missing].lower():
            print('Correct!')
        else:
            if elem_missing == 0:
                similar = [x.lower() for (x,y,z) in relations if y.lower()==relation[1].lower() and z.lower()==relation[2].lower()]
                possible_answers = similar
                if answer.lower() in possible_answers:
                    print('Correct')
                    # print(possible_answers)
                    continue
            elif elem_missing == 1:
                similar = [y.lower() for (x,y,z) in relations if x.lower()==relation[0].lower() and z.lower()==relation[2].lower()]
                possible_answers = similar
                if answer.lower() in possible_answers:
                    print('Correct')
                    # print(possible_answers)
                    continue
            elif elem_missing == 2:
                similar = [z.lower() for (x,y,z) in relations if x.lower()==relation[0].lower() and y.lower()==relation[1].lower()]
                possible_answers = similar
                if answer.lower() in possible_answers:
                    print('Correct')
                    # print(possible_answers)
                    continue
            print('Incorrect!')


############## WordNet ##############

## 4)
def search_wordnet_synsets():
    while True:
        print('Press 0 to stop or the word to continue...')

        word = input().strip()
        if word.startswith('0'):
            break

        print(f'\'{word}\' synsets are: ')
        for synset in wordnet.synsets(word):
            print(synset)

## 5)
def question_ontology_wordnet(ontology_file='food.txt'):
    relations = []
    with open(ontology_file, 'r') as of:
        for line in of.readlines():
            relations += [tuple(re.split(" ", line.strip()))]
    
    while True:
        print('Press q to stop or other key to continue...')

        key = input()
        if key.startswith('q'):
            print('Program ending...')
            break

        relation = relations[int(random.random()*(len(relations)-1))]
        elem_missing = int(random.random()*2)
        if elem_missing == 1:
            print(f'What is the relationship between {relation[0]} and {relation[2]}?')
        elif elem_missing == 0:
            print(f'Who is in relationship {relation[1]} with {relation[2]}?')
        elif elem_missing == 2:
            print(f'{relation[0]} is in relationship {relation[1]} with whom?')

        answer = input().strip()
        possible_answers = [relation[elem_missing].lower()]
        if len(wordnet.synsets(relation[elem_missing].lower())) > 0:
            possible_answers += wordnet.synsets(relation[elem_missing].lower())[0].lemma_names()
        if answer.lower() in possible_answers:
            print('Correct!')
            # print(possible_answers)
        else:
            # print(possible_answers)
            if elem_missing == 0:
                similar = [x.lower() for (x,y,z) in relations if y.lower()==relation[1].lower() and z.lower()==relation[2].lower()]
                similar_syn = []
                for x in similar:
                    if len(wordnet.synsets(x.lower())) > 0:
                        similar_syn += wordnet.synsets(x.lower())[0].lemma_names()
                possible_answers = similar + similar_syn
                if answer.lower() in possible_answers:
                    print('Correct')
                    # print(possible_answers)
                    continue
            elif elem_missing == 1:
                similar = [y.lower() for (x,y,z) in relations if x.lower()==relation[0].lower() and z.lower()==relation[2].lower()]
                similar_syn = []
                for x in similar:
                    if len(wordnet.synsets(x.lower())) > 0:
                        similar_syn += wordnet.synsets(x.lower())[0].lemma_names()
                possible_answers = similar + similar_syn
                if answer.lower() in possible_answers:
                    print('Correct')
                    # print(possible_answers)
                    continue
            elif elem_missing == 2:
                similar = [z.lower() for (x,y,z) in relations if x.lower()==relation[0].lower() and y.lower()==relation[1].lower()]
                similar_syn = []
                for x in similar:
                    if len(wordnet.synsets(x.lower())) > 0:
                        similar_syn += wordnet.synsets(x.lower())[0].lemma_names()
                possible_answers = similar + similar_syn
                if answer.lower() in possible_answers:
                    print('Correct')
                    # print(possible_answers)
                    continue
            print('Incorrect!')
            # print(possible_answers)

## bonus:
def question_wn_hyper_mero(ontology_file='food.txt'):
    relations = []
    with open(ontology_file, 'r') as of:
        for line in of.readlines():
            relations += [tuple(re.split(" ", line.strip()))]
    
    while True:
        print('Press q to stop or other key to continue...')

        key = input()
        if key.startswith('q'):
            print('Program ending...')
            break

        relation = relations[int(random.random()*(len(relations)-1))]
        elem_missing = int(random.random()*2)
        if elem_missing == 1:
            print(f'What is the relationship between {relation[0]} and {relation[2]}?')
        elif elem_missing == 0:
            print(f'Who is in relationship {relation[1]} with {relation[2]}?')
        elif elem_missing == 2:
            print(f'{relation[0]} is in relationship {relation[1]} with whom?')

        answer = input().strip()
        possible_answers = [relation[elem_missing].lower()]
        if len(wordnet.synsets(relation[elem_missing].lower())) > 0:
            possible_answers += wordnet.synsets(relation[elem_missing].lower())[0].lemma_names()
        if answer.lower() in possible_answers:
            print('Correct!')
            # print(possible_answers)
        else:
            # print(possible_answers)
            if elem_missing == 0:
                similar = [x.lower() for (x,y,z) in relations if y.lower()==relation[1].lower() and z.lower()==relation[2].lower()]
                similar_syn = []
                for x in similar:
                    if len(wordnet.synsets(x.lower())) > 0:
                        similar_syn += wordnet.synsets(x.lower())[0].lemma_names()
                        if len(wordnet.synsets(x.lower())[0].hypernyms()) > 0:
                            similar_syn += wordnet.synsets(x.lower())[0].hypernyms()[0].lemma_names()
                        if len(wordnet.synsets(x.lower())[0].part_meronyms()) > 0:
                            similar_syn += wordnet.synsets(x.lower())[0].part_meronyms()[0].lemma_names()
                possible_answers = similar + similar_syn
                if answer.lower() in possible_answers:
                    print('Correct')
                    # print(possible_answers)
                    continue
            elif elem_missing == 1:
                similar = [y.lower() for (x,y,z) in relations if x.lower()==relation[0].lower() and z.lower()==relation[2].lower()]
                similar_syn = []
                for x in similar:
                    if len(wordnet.synsets(x.lower())) > 0:
                        similar_syn += wordnet.synsets(x.lower())[0].lemma_names()
                        if len(wordnet.synsets(x.lower())[0].hypernyms()) > 0:
                            similar_syn += wordnet.synsets(x.lower())[0].hypernyms()[0].lemma_names()
                        if len(wordnet.synsets(x.lower())[0].part_meronyms()) > 0:
                            similar_syn += wordnet.synsets(x.lower())[0].part_meronyms()[0].lemma_names()
                possible_answers = similar + similar_syn
                if answer.lower() in possible_answers:
                    print('Correct')
                    # print(possible_answers)
                    continue
            elif elem_missing == 2:
                similar = [z.lower() for (x,y,z) in relations if x.lower()==relation[0].lower() and y.lower()==relation[1].lower()]
                similar_syn = []
                for x in similar:
                    if len(wordnet.synsets(x.lower())) > 0:
                        similar_syn += wordnet.synsets(x.lower())[0].lemma_names()
                        if len(wordnet.synsets(x.lower())[0].hypernyms()) > 0:
                            similar_syn += wordnet.synsets(x.lower())[0].hypernyms()[0].lemma_names()
                        if len(wordnet.synsets(x.lower())[0].part_meronyms()) > 0:
                            similar_syn += wordnet.synsets(x.lower())[0].part_meronyms()[0].lemma_names()
                possible_answers = similar + similar_syn
                if answer.lower() in possible_answers:
                    print('Correct')
                    # print(possible_answers)
                    continue
            
            print('Incorrect!')
            # print(possible_answers)

def test_wn():
    # print(wordnet.synsets('disjointWith'))
    # print(wordnet.synsets('disjointWith')[0].lemma_names())
    print(wordnet.synsets('fowl')[0].lemma_names())
    print(wordnet.synsets('fowl')[0].hypernyms()[0].lemma_names())
    print(wordnet.synsets('fowl')[0].part_meronyms()[0].lemma_names())

if __name__ == '__main__':
    # print_ontology_triplets()
    # question_ontology()

    # search_wordnet_synsets()
    # nltk.download()

    # question_ontology_wordnet()

    # question_wn_hyper_mero()

    test_wn()