from enum import Enum

class Positions(Enum):
    TOP_LEFT = (-1, -1)
    TOP_MID = (0, -1)
    TOP_RIGHT = (1, -1)
    MID_LEFT = (-1, 0)
    MID_RIGHT = (1, 0)
    BOTTOM_LEFT = (-1, 1)
    BOTTOM_MID = (0, 1)
    BOTTOM_RIGHT = (1,1)

def next_life_cycle(cells: list):
    new_cells = [[False for _ in range(len(cells[0]))] for _ in range(len(cells))]
    for row in range(len(cells)):
        for entry in range(len(cells[0])):
            #get all cases in position enum
            indicies = [(row + e.value[0], entry + e.value[1]) for e in Positions]
            num_alive = 0
            for index in indicies:
                if (index[0] >= 0 and index[0] < len(cells)) and (index[1] >= 0 and index[1] < len(cells[0])):
                    #if adjacent cell if alive, increase alive cell count
                    num_alive += 1 if cells[index[0]][index[1]] else 0
            if num_alive < 2 or num_alive > 3:
                new_cells[row][entry] = False
            else:
                new_cells[row][entry] = True
    return new_cells


counter = 0
next_arr = list()
while counter < 10:
    if counter == 0:
        next_arr = next_life_cycle([[True if _%2 == 0 else False for _ in range(4) ] for _ in range(4)])
    else:
        next_arr = next_life_cycle(next_arr)
    for row in next_arr:
        print(str(row) + "\n")
    counter += 1
    print("\n")
    
