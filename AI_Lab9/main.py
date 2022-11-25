def define_next_states():
  next_states={}
  for i in range(1, 13):
    for j in range(1, 5):
      next_states[((i,j), "up")] = (i-1, j)
      next_states[((i,j), "down")] = (i+1, j)
      next_states[((i,j), "left")] = (i, j-1)
      next_states[((i,j), "right")] = (i, j+1)
  return next_states


def init_q_table():
  q_table={}
  for i in range(1, 13):
    for j in range(1, 5):
      q_table[((i,j), "up")] = 0
      q_table[((i,j), "down")] = 0
      q_table[((i,j), "left")] = 0
      q_table[((i,j), "right")] = 0
  return q_table


def init_grid():
  grid = [[-1 for i in range(14)] for j in range(6)]
  for i in range(2, 12):
    grid[4][i] = -100
  for i in range(14):
    grid[0][i]=-999
    grid[5][i]=-999
  for j in range(6):
    grid[j][0]=-999
    grid[j][13]=-999
  return grid


def print_next_states(next_states):
  for key in next_states:
    print(key, " - ", next_states[key])


def print_grid(grid):
  for i in grid:
    for j in i:
      print(f'%-6s'%str(j), end="")
    print()


def init_params():
  grid = init_grid()
  q_table = init_q_table()
  learn_rate = 0.01
  discount_factor = 0.9
  episodes_count = 100
  max_steps_per_episode = 1000
  initial_state = (4,1)
  final_state = (4,12)
  next_states = define_next_states()

  return grid, q_table, next_states


grid, q_table, next_states = init_params()
print_grid(grid)
print_next_states(next_states)