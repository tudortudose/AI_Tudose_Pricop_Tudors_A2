import pygame
import matplotlib.pyplot as plt

BLACK = (0, 0, 0)
WHITE = (255, 255, 255)
GREEN = (0, 255, 0)
RED = (255, 0, 0)
WINDOW_HEIGHT = 400
WINDOW_WIDTH = 1200


def init_screen():
    pygame.init()
    pygame.font.init()
    screen = pygame.display.set_mode((WINDOW_WIDTH, WINDOW_HEIGHT))
    screen.fill(BLACK)
    return screen


def draw_grid(current_state, q_table, screen):
    x_grid_size = 12
    y_grid_size = 4
    block_size = 100
    text_padding = 30
    font = pygame.font.SysFont('Arial', 14)

    for x in range(x_grid_size):
        for y in range(y_grid_size):
            if y == 3 and 0 < x < 11:
                color = RED
                border = 0
                rect = pygame.Rect(x * block_size + 1, y * block_size + 1, block_size - 2, block_size - 2)
                pygame.draw.rect(screen, color, rect, border)
            else:
                color = BLACK
                border = 0
                rect = pygame.Rect(x * block_size + 1, y * block_size + 1, block_size - 2, block_size - 2)
                pygame.draw.rect(screen, color, rect, border)

            color = WHITE
            border = 1

            rect = pygame.Rect(x * block_size, y * block_size, block_size, block_size)
            pygame.draw.rect(screen, color, rect, border)

            pygame.draw.line(screen, color, (x * block_size, y * block_size),
                             ((x + 1) * block_size, (y + 1) * block_size), 2)

            pygame.draw.line(screen, color, (x * block_size, (y + 1) * block_size),
                             ((x + 1) * block_size, y * block_size), 2)

            if ((y + 1, x + 1), "left") in q_table:
                value = str(round(q_table[((y + 1, x + 1), "left")], 2))
                text = font.render(value, True, GREEN)
                rect_text = text.get_rect()
                rect_text.center = (x * block_size + block_size // 2 - text_padding, y * block_size + block_size // 2)
                screen.blit(text, rect_text)

            if ((y + 1, x + 1), "right") in q_table:
                value = str(round(q_table[((y + 1, x + 1), "right")], 2))
                text = font.render(value, True, GREEN)
                rect_text = text.get_rect()
                rect_text.center = (x * block_size + block_size // 2 + text_padding, y * block_size + block_size // 2)
                screen.blit(text, rect_text)

            if ((y + 1, x + 1), "up") in q_table:
                value = str(round(q_table[((y + 1, x + 1), "up")], 2))
                text = font.render(value, True, GREEN)
                rect_text = text.get_rect()
                rect_text.center = (x * block_size + block_size // 2, y * block_size + block_size // 2 - text_padding)
                screen.blit(text, rect_text)

            if ((y + 1, x + 1), "down") in q_table:
                value = str(round(q_table[((y + 1, x + 1), "down")], 2))
                text = font.render(value, True, GREEN)
                rect_text = text.get_rect()
                rect_text.center = (x * block_size + block_size // 2, y * block_size + block_size // 2 + text_padding)
                screen.blit(text, rect_text)

            circle_pos = ((current_state[1] - 1) * block_size + block_size // 2,
                          (current_state[0] - 1) * block_size + block_size // 2)
            pygame.draw.circle(screen, GREEN, circle_pos, block_size // 4)


def draw_graphic(reward_list):
    fig, ax = plt.subplots(figsize=(12, 5))
    ax.set_title('Rewards Convergence')
    ax.set_xlabel('Episode')
    ax.set_ylabel('Reward')

    ax.plot(reward_list, color='green')

    ax.yaxis.grid(color='lightgray', linestyle='dashed')
    ax.xaxis.grid(color='lightgray', linestyle='dashed')
    plt.tight_layout()
    plt.show()