#The Game of Life - Multithreading
#Author: Georgina Resendiz

import random, pygame, sys, threading
from pygame.locals import *

# Constants
FPS = 30        # frames per second
SIZE_CELL = 40  # pixels of each cell nxn
W_CELLS = 12 # cells width 
H_CELLS = 12 # cells height

MATRIX = []
for x in range(W_CELLS):
    MATRIX.append([0] * H_CELLS) # Initialize MATRIX with zeros

MATRIX_LOCKS = [] 
for x in range(W_CELLS):
    column = []
    for y in range(H_CELLS):
        column.append(threading.Lock()) # Generate one Lock object for each cell
    MATRIX_LOCKS.append(column)

# Constants colors
#             R    G    B
WHITE     = (255, 255, 255)
BLACK     = (  0,   0,   0)
DARKGRAY  = ( 40,  40,  40)
BACKGROUND_COLOR = BLACK  # Background color
LINES_MATRIX = DARKGRAY # MATRIX lines color

# Total pixels of window width and height
W_WINDOW = SIZE_CELL * W_CELLS
H_WINDOW = SIZE_CELL * H_CELLS

# Global variable - Quit game - Exit threads
CELLS_RUNNING = True

# Class Cell
class Cell(threading.Thread): # "Thread" is a class in the "threading" module
    def __init__(self, name = 'Cell', value = None, speed = None, positionx = None, positiony = None):

        threading.Thread.__init__(self) # Thread class - Calling its __init__() method

        # Initialize Default Cell Settings
        self.name = name # Name of cell
        self.value = value # Value 0 or 1
        self.speed = speed # Speed of cell
        self.positionx = positionx # Position x in MATRIX
        self.positiony = positiony # Position y in MATRIX

        startx = self.positionx
        starty = self.positiony

        MATRIX_LOCKS[startx][starty].acquire()
        MATRIX[startx][starty] = self.value
        MATRIX_LOCKS[startx][starty].release()


    def run(self): # Threads code

        while True:
            if not CELLS_RUNNING:
                return # Thread Cell terminates when run() returns

            x = self.positionx
            y = self.positiony
            total_sum = 0

            # Position of 8 Neighbbors around
            neighbor1_x = (x-1+W_CELLS)%(W_CELLS)
            neighbor1_y = (y-1+W_CELLS)%(W_CELLS)
            neighbor2_x = (x+W_CELLS)%(W_CELLS)
            neighbor2_y = (y-1+W_CELLS)%(W_CELLS)
            neighbor3_x = (x+1+W_CELLS)%(W_CELLS)
            neighbor3_y = (y-1+W_CELLS)%(W_CELLS)
            neighbor4_x = (x-1+W_CELLS)%(W_CELLS)
            neighbor4_y = (y+W_CELLS)%(W_CELLS)
            neighbor5_x = (x+1+W_CELLS)%(W_CELLS)
            neighbor5_y = (y+W_CELLS)%(W_CELLS)
            neighbor6_x = (x-1+W_CELLS)%(W_CELLS)
            neighbor6_y = (y+1+W_CELLS)%(W_CELLS)
            neighbor7_x = (x+W_CELLS)%(W_CELLS)
            neighbor7_y = (y+1+W_CELLS)%(W_CELLS)
            neighbor8_x = (x+1+W_CELLS)%(W_CELLS)
            neighbor8_y = (y+1+W_CELLS)%(W_CELLS)

            # Total sum of 8 Neighbors' values
            MATRIX_LOCKS[x][y].acquire()
            MATRIX_LOCKS[neighbor1_x][neighbor1_y].acquire()
            MATRIX_LOCKS[neighbor2_x][neighbor2_y].acquire()
            MATRIX_LOCKS[neighbor3_x][neighbor3_y].acquire()
            MATRIX_LOCKS[neighbor4_x][neighbor4_y].acquire()
            MATRIX_LOCKS[neighbor5_x][neighbor5_y].acquire()
            MATRIX_LOCKS[neighbor6_x][neighbor6_y].acquire()
            MATRIX_LOCKS[neighbor7_x][neighbor7_y].acquire()
            MATRIX_LOCKS[neighbor8_x][neighbor8_y].acquire()

            total_sum = MATRIX[neighbor1_x][neighbor1_y]+MATRIX[neighbor2_x][neighbor2_y]+MATRIX[neighbor3_x][neighbor3_y]+MATRIX[neighbor4_x][neighbor4_y]+MATRIX[neighbor5_x][neighbor5_y]+MATRIX[neighbor6_x][neighbor6_y]+MATRIX[neighbor7_x][neighbor7_y]+MATRIX[neighbor8_x][neighbor8_y]   

            MATRIX_LOCKS[x][y].release()
            MATRIX_LOCKS[neighbor1_x][neighbor1_y].release()
            MATRIX_LOCKS[neighbor2_x][neighbor2_y].release()
            MATRIX_LOCKS[neighbor3_x][neighbor3_y].release()
            MATRIX_LOCKS[neighbor4_x][neighbor4_y].release()
            MATRIX_LOCKS[neighbor5_x][neighbor5_y].release()
            MATRIX_LOCKS[neighbor6_x][neighbor6_y].release()
            MATRIX_LOCKS[neighbor7_x][neighbor7_y].release()
            MATRIX_LOCKS[neighbor8_x][neighbor8_y].release()

            # Cell Rules
            MATRIX_LOCKS[x][y].acquire()
            if (MATRIX[x][y] == 1): # Living cell
                if (total_sum< 2 or total_sum > 3): # 2 or 3 living cells around
                    new_value = 0 # Cell dies
                else:
                    new_value = 1 # Cell remains living
            else: # Dead Cell
                if (total_sum == 3): # 3 living cells around
                    new_value = 1 # Cell lives
                else:
                    new_value = 0 # Cell remains dead
            MATRIX[x][y] = new_value
            MATRIX_LOCKS[x][y].release()

            pygame.time.wait(self.speed) # Put thread to sleep for a while
            

def main():
    global FPSCLOCK, DISPLAY_SURFACE, SPEED 

    # Pygame window
    pygame.init()
    FPSCLOCK = pygame.time.Clock()
    DISPLAY_SURFACE = pygame.display.set_mode((W_WINDOW, H_WINDOW))
    SPEED = 500
    i=0
    pygame.display.set_caption('The Game of Life - Multithreading - By Georgina Resendiz')

    matrixStart() # Start matrix

    # Generate Cell objects - Threads
    cell_list = [] 
    for x in range(0, W_CELLS):
        for y in range(0, H_CELLS):
                cell_list.append(Cell(name = 'Cell_%s' % i, value = MATRIX[x][y], speed = SPEED, positionx = x, positiony = y)) #Different names to identify threads
                cell_list[-1].start() # Start the cell code in its own thread
                i += 1

    DISPLAY_SURFACE.fill(BACKGROUND_COLOR) # Black background 
    while True: # Main loop
        handleEvents()  # If the user quits the game, every thread terminates
        drawMATRIX() # Draw the Matrix in Grid
        displayText()
        pygame.display.update() # Update the display
        FPSCLOCK.tick(FPS) # Update the display - 30 Frames Per Second

def matrixStart():

    for x in range(0, W_CELLS):
        for y in range(0, H_CELLS):
            MATRIX_LOCKS[x][y].acquire()
            MATRIX[x][y] = random.randint(0,1)
            MATRIX_LOCKS[x][y].release()

def handleEvents():
    global CELLS_RUNNING

    # Events 
    for event in pygame.event.get(): # Event handling loop
        # Quit game
        if (event.type == QUIT) or (event.type == KEYDOWN and event.key == K_ESCAPE):
            CELLS_RUNNING = False # False tells the cells threads to exit
            pygame.quit()
            sys.exit()

        # Clear Matrix
        if (event.type == pygame.KEYDOWN):
            if(event.key == pygame.K_0):

                for x in range(0, W_CELLS):
                    for y in range(0, H_CELLS):
                        MATRIX_LOCKS[x][y].acquire()
                        MATRIX[x][y] = 0
                        MATRIX_LOCKS[x][y].release()

        # Random Matrix
            elif(event.key == pygame.K_1):

                for x in range(0, W_CELLS):
                    for y in range(0, H_CELLS):
                        MATRIX_LOCKS[x][y].acquire()
                        MATRIX[x][y] = random.randint(0,1) 
                        MATRIX_LOCKS[x][y].release()

        # Template - Still Lifes
            elif(event.key == pygame.K_2):

                for x in range(0, W_CELLS):
                    for y in range(0, H_CELLS):
                        MATRIX_LOCKS[x][y].acquire()
                        MATRIX[x][y] = 0
                        MATRIX_LOCKS[x][y].release()

                MATRIX_LOCKS[2][1].acquire()
                MATRIX_LOCKS[3][1].acquire()
                MATRIX_LOCKS[2][2].acquire()
                MATRIX_LOCKS[3][2].acquire()
                MATRIX_LOCKS[6][2].acquire()
                MATRIX_LOCKS[7][1].acquire()
                MATRIX_LOCKS[8][2].acquire()
                MATRIX_LOCKS[9][2].acquire()
                MATRIX_LOCKS[7][3].acquire()
                MATRIX_LOCKS[8][3].acquire()
                MATRIX_LOCKS[4][7].acquire()
                MATRIX_LOCKS[5][8].acquire()
                MATRIX_LOCKS[6][9].acquire()
                MATRIX_LOCKS[5][6].acquire()
                MATRIX_LOCKS[6][6].acquire()
                MATRIX_LOCKS[7][7].acquire()
                MATRIX_LOCKS[7][8].acquire()

                MATRIX[2][1] = 1
                MATRIX[3][1] = 1
                MATRIX[2][2] = 1
                MATRIX[3][2] = 1

                MATRIX[6][2] = 1
                MATRIX[7][1] = 1
                MATRIX[8][1] = 1
                MATRIX[9][2] = 1
                MATRIX[7][3] = 1
                MATRIX[8][3] = 1

                MATRIX[4][7] = 1
                MATRIX[5][8] = 1
                MATRIX[6][9] = 1
                MATRIX[5][6] = 1
                MATRIX[6][6] = 1
                MATRIX[7][7] = 1
                MATRIX[7][8] = 1

                MATRIX_LOCKS[2][1].release()
                MATRIX_LOCKS[3][1].release()
                MATRIX_LOCKS[2][2].release()
                MATRIX_LOCKS[3][2].release()
                MATRIX_LOCKS[6][2].release()
                MATRIX_LOCKS[7][1].release()
                MATRIX_LOCKS[8][2].release()
                MATRIX_LOCKS[9][2].release()
                MATRIX_LOCKS[7][3].release()
                MATRIX_LOCKS[8][3].release()
                MATRIX_LOCKS[4][7].release()
                MATRIX_LOCKS[5][8].release()
                MATRIX_LOCKS[6][9].release()
                MATRIX_LOCKS[5][6].release()
                MATRIX_LOCKS[6][6].release()
                MATRIX_LOCKS[7][7].release()
                MATRIX_LOCKS[7][8].release()

        # Draw Matrix
        if (event.type == MOUSEBUTTONDOWN):
            mouse_x, mouse_y = pygame.mouse.get_pos()

            x = (mouse_x // (SIZE_CELL)) # Int Division
            y = (mouse_y // (SIZE_CELL)) # Int Division

            MATRIX_LOCKS[x][y].acquire()
            MATRIX[x][y] = 1
            MATRIX_LOCKS[x][y].release()

        
 
def drawMATRIX():
    # Draw the MATRIX lines
    for x in range(0, W_WINDOW, SIZE_CELL): # Draw vertical lines
        pygame.draw.line(DISPLAY_SURFACE, LINES_MATRIX, (x, 0), (x, H_WINDOW))
    for y in range(0, H_WINDOW, SIZE_CELL): # Draw horizontal lines
        pygame.draw.line(DISPLAY_SURFACE, LINES_MATRIX, (0, y), (W_WINDOW, y))

    # For drawing living and dead cells, all matrix values
    for x in range(0, W_CELLS):
        for y in range(0, H_CELLS):
            gotLock = MATRIX_LOCKS[x][y].acquire(timeout=0.02) 
            if not gotLock:
                continue

            if MATRIX[x][y] is None: # If there is no value, draw a dead cell
                pygame.draw.rect(DISPLAY_SURFACE, BLACK, (x * SIZE_CELL + 1, y * SIZE_CELL + 1, SIZE_CELL - 1, SIZE_CELL - 1))
                MATRIX_LOCKS[x][y].release() 
            else: 
                value = MATRIX[x][y] # Read the MATRIX data structure
                MATRIX_LOCKS[x][y].release() 

                if (value == 1):
                    color = WHITE
                else:
                    color = BLACK

                # Draw cells
                pygame.draw.rect(DISPLAY_SURFACE, LINES_MATRIX, (x * SIZE_CELL,     y * SIZE_CELL,     SIZE_CELL,     SIZE_CELL))
                pygame.draw.rect(DISPLAY_SURFACE, color,       (x * SIZE_CELL + 2, y * SIZE_CELL + 2, SIZE_CELL - 4, SIZE_CELL - 4))

# Display number of Living Cells
def displayText():
    Font = pygame.font.SysFont("Arial",20)
    num_living_cells = 0

    for x in range(0, W_CELLS):
        for y in range(0, H_CELLS):
            MATRIX_LOCKS[x][y].acquire()
            if (MATRIX[x][y] == 1):
                num_living_cells += 1
            MATRIX_LOCKS[x][y].release()


    num_live_cells = Font.render("Living cells: "+str(num_living_cells),0,(0,255,0))
    DISPLAY_SURFACE.blit(num_live_cells, (0,0))

# main
if __name__ == '__main__':
    main()