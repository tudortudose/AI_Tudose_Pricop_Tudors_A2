from random import shuffle
import numpy as np

def read_data(filename):
    with open(filename, 'r') as fd:
        input = []
        for readline in fd:
            input += [readline.strip()]
        shuffle(input)

    for line in input:
        print(line)

    return input

def split_train_test(input_data):
    split_point = len(input_data)*3/4
    return (input_data[:split_point],input_data[split_point:])

def init_params(input_data):
    first_layer_count = input_data[0].count(',') + 1

    output_values = set()
    for instance in input_data:
        output_values.add(instance[instance.rfind(','):])
    output_layer_count = len(output_values)

    hidden_layer_count = int(2/3*(first_layer_count+output_count))

    learn_rate = 0.001
    max_epochs = 100

    weights_to_hidden = 2*np.random.random((output_count, weights_count)) - 1
    weights_to_output = 2*np.random.random((output_count, hidden_count)) - 1

    return (weights_to_hidden)


read_data('iris.data')