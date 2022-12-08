from graphics import *
import sys
import time

ACTIONS = ["up", "down", "left", "right"]


def init_q_table_and_next_states():
    q_table = {}
    next_states = {}
    for i in range(1, 5):
        for j in range(1, 13):
            if i != 1:
                q_table[((i, j), "up")] = 0
                next_states[((i, j), "up")] = (i - 1, j)
            if i != 4:
                q_table[((i, j), "down")] = 0
                next_states[((i, j), "down")] = (i + 1, j)
            if j != 1:
                q_table[((i, j), "left")] = 0
                next_states[((i, j), "left")] = (i, j - 1)
            if j != 12:
                q_table[((i, j), "right")] = 0
                next_states[((i, j), "right")] = (i, j + 1)
    return q_table, next_states


def init_grid():
    grid = [[-1 for i in range(14)] for j in range(6)]
    for i in range(2, 12):
        grid[4][i] = -100
    for i in range(14):
        grid[0][i] = -999
        grid[5][i] = -999
    for j in range(6):
        grid[j][0] = -999
        grid[j][13] = -999

    grid[4][12] = 100
    return grid


def print_next_states(next_states):
    for key in next_states:
        print(key, " - ", next_states[key])


def print_grid(grid):
    for i in grid:
        for j in i:
            print(f'%-6s' % str(j), end="")
        print()


def init_params():
    grid = init_grid()
    q_table, next_states = init_q_table_and_next_states()
    learn_rate = 0.1
    discount_factor = 0.9
    episodes_count = 200
    max_steps_per_episode = 1000
    initial_state = (4, 1)
    final_state = (4, 12)

    return grid, q_table, next_states, learn_rate, discount_factor, episodes_count, max_steps_per_episode, initial_state, final_state


def get_next_state(current_state, q_table, next_states):
    max_val = -9999
    max_state = None
    max_action = None
    for action in ACTIONS:
        if (current_state, action) in q_table:
            val = q_table[(current_state, action)]
            if val > max_val:
                max_val = val
                max_state = next_states[(current_state, action)]
                max_action = action
    return max_action, max_state


def get_max_q(state, q_table):
    max_val = -9999
    for action in ACTIONS:
        if (state, action) in q_table:
            val = q_table[(state, action)]
            if val > max_val:
                max_val = val
    return max_val


def different_states(state1, state2):
    return not (state1[0] == state2[0] and state1[1] == state2[1])


def train(grid, q_table, next_states, learn_rate, discount_factor, episodes_count, max_steps_per_episode, initial_state,
          final_state, screen):
    reward_list = []
    while episodes_count > 0:
        current_state = initial_state
        current_step_per_episode = 0
        current_reward = 0

        while different_states(current_state, final_state) and current_step_per_episode < max_steps_per_episode:
            action, next_state = get_next_state(current_state, q_table, next_states)

            curr_q = q_table[(current_state, action)]
            reward = grid[next_state[0]][next_state[1]]
            current_reward += reward

            new_q = curr_q + learn_rate * (reward + discount_factor * get_max_q(next_state, q_table) - curr_q)

            q_table[(current_state, action)] = new_q
            current_state = next_state

            if reward == -100:
                break

            draw_grid(current_state, q_table, screen)
            pygame.display.update()

            current_step_per_episode += 1
            time.sleep(0.001)
        reward_list += [current_reward]
        episodes_count -= 1
    return q_table, next_states, reward_list


def init_and_train(screen):
    trained_table, next_states, reward_list = train(*init_params(), screen)
    return trained_table, next_states, reward_list


def print_policy(q_table, next_states):
    print("Policy:")
    for i in range(1, 5):
        for j in range(1, 13):
            max_action, max_state = get_next_state((i, j), q_table, next_states)
            print("State: {} - Action: {} - Next: {} ".format((i, j), max_action, max_state))


def main():
    screen = init_screen()
    q_table, next_states, reward_list = init_and_train(screen)
    print_policy(q_table, next_states)
    draw_graphic(reward_list)

    while True:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()


main()
