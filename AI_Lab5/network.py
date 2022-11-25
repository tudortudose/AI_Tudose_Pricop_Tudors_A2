import numpy as np


class Network():

  def __init__(self, input_layer_size, hidden_layer_size, output_layer_size):
    hidden_layer_st_dev = 1 / np.sqrt(input_layer_size)
    output_layer_st_dev = 1 / np.sqrt(hidden_layer_size)
    self.hidden_layer_wheights = np.random.normal(scale = hidden_layer_st_dev, size = (hidden_layer_size, input_layer_size))
    self.output_layer_wheights = np.random.normal(scale = output_layer_st_dev, size = (output_layer_size, hidden_layer_size))
    self.hidden_layer_biases = np.zeros((hidden_layer_size, 1))
    self.output_layer_biases = np.zeros((output_layer_size, 1))


  def test(self, data_set):
    size = len(data_set[0])
    correct_count = 0
    actual = []
    predicted = []
    for i in range(size):
      input_layer = data_set[0][i]
      input_target = data_set[1][i]
      binary_target = np.array([0 for x in range(3)])
      binary_target[input_target] = 1

      # transform input and target from vectors to matrices with one column for an easier multiplication
      input_layer.shape += (1,)
      binary_target.shape += (1,)

      # Forward Propagation
      hidden_layer_preactivation = self.hidden_layer_biases + self.hidden_layer_wheights @ input_layer
      hidden_layer_activation = 1 / (1 + np.exp(-hidden_layer_preactivation))
      
      output_layer_preactivation = self.output_layer_biases + self.output_layer_wheights @ hidden_layer_activation
      output_layer_activation = 1 / (1 + np.exp(-output_layer_preactivation))

      correct_count += int(np.argmax(output_layer_activation) == np.argmax(binary_target))
      actual += [np.argmax(output_layer_activation)]
      predicted += [np.argmax(binary_target)]

    print(f"Test Accuracy: {round((correct_count / size) * 100, 2)}%")
    return (np.array(actual), np.array(predicted))

  def train(self, data_set, learning_rate, epochs_count):
    size = len(data_set[0])
    correct_count = 0
    for epoch in range(epochs_count):
      for i in range(size):
        input_layer = data_set[0][i]
        input_target = data_set[1][i]
        binary_target = np.array([0 for x in range(3)])
        binary_target[input_target] = 1
        
        # transform input and target from vectors to matrices with one line for multiplication
        input_layer.shape += (1,)
        binary_target.shape += (1,)

        # Forward Propagation
        hidden_layer_preactivation = self.hidden_layer_biases + self.hidden_layer_wheights @ input_layer
        hidden_layer_activation = 1 / (1 + np.exp(-hidden_layer_preactivation))
        
        output_layer_preactivation = self.output_layer_biases + self.output_layer_wheights @ hidden_layer_activation
        output_layer_activation = 1 / (1 + np.exp(-output_layer_preactivation))

        correct_count += int(np.argmax(output_layer_activation) == np.argmax(binary_target))

        # Backpropagation
        output_layer_delta = output_layer_activation - binary_target
        self.output_layer_wheights += -learning_rate * output_layer_delta @ np.transpose(hidden_layer_activation)
        self.output_layer_biases += -learning_rate * output_layer_delta
        
        hidden_layer_delta = np.transpose(self.output_layer_wheights) @ output_layer_delta * (hidden_layer_activation * (1 - hidden_layer_activation))
        self.hidden_layer_wheights += -learning_rate * hidden_layer_delta @ np.transpose(input_layer)
        self.hidden_layer_biases += -learning_rate * hidden_layer_delta

      print(f"Training Accuracy (epoch {epoch}): {round((correct_count / size) * 100, 2)}%")
      correct_count = 0