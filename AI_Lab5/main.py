from random import shuffle
from network import Network
import numpy as np


def read_data(filename):
    with open(filename, 'r') as fd:
        data_input = []
        data_labels = []
        input_strings = []
        for readline in fd:
            string = readline.strip()
            input_strings += [string]
        shuffle(input_strings)
        shuffle(input_strings)

        labels_dict = {}
        for string in input_strings:
          input_arr = string.split(',')
          data_input += [np.array(list(map(lambda x: float(x), input_arr[:-1])))]
          label_key = input_arr[-1]
          lable_value = labels_dict.setdefault(label_key, len(labels_dict))
          data_labels += [lable_value]
        
    return (np.array(data_input), np.array(data_labels))


def split_input_data(input_data):
    split_point = int(len(input_data[0])*9/10)
    train_set = (input_data[0][:split_point], input_data[1][:split_point])
    test_set = (input_data[0][split_point:], input_data[1][split_point:])
    return train_set, test_set


def init_layer_counts(input_data):
    input_layer_count = len(input_data[0][0])

    output_layer_count = max(input_data[1]) + 1

    hidden_layer_count = int(2/3*(input_layer_count+output_layer_count))

    return input_layer_count, hidden_layer_count, output_layer_count


def print_set(data):
  for i in range (len(data[0])):
    print (data[0][i], data[1][i])


input_data = read_data('iris.data')
train_set, test_set = split_input_data(input_data)
print_set(train_set)
network = Network(*init_layer_counts(input_data))
network.train(train_set, 0.01, 100)
network.test(test_set)