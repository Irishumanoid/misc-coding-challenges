#inputs is a 2D array of spaces, chars, and newlines
#returns list of characters and starting and ending angles
import math
import numpy as np


def from_pie_chart(radius, letters):
    start_end_angles = {}
    center_loc = math.floor(len(letters)/2)
    for width in range(len(letters)):
        for depth in range(len(letters[0])):
            distance = math.sqrt((depth - center_loc)**2 + (width - center_loc)**2)
            if distance < radius and letters[width][depth] != " ":
                loc = rectangular_to_polar(width, depth)
                angle = loc[1]
                char_val = letters[width][depth]
                if not start_end_angles.keys().__contains__(char_val):
                    start_end_angles[char_val] = [angle, angle]
                else: 
                    if min(angle, start_end_angles[char_val][0]) == angle:
                        start_end_angles[char_val] = [angle, start_end_angles[char_val][1]]
                    elif max(angle, start_end_angles[char_val][1]) == angle:
                        start_end_angles[char_val] = [start_end_angles[char_val][0], angle]
    for key in start_end_angles.keys():
        start_end_angles[key][0] *= 180
        start_end_angles[key][1] *= 180
    print(start_end_angles)

def to_pie_chart(data: dict):
    radius = data["radius"]
    pie_chart = [[" " for _ in range(2*radius)] for _ in range(2*radius)]
    for row in range(len(pie_chart)):
        for column in range(len(pie_chart[0])):
            #get current angle
            cur_angle = math.atan2(abs(column-radius), abs(row-radius)) #sketchy check this
            print(cur_angle*180/math.pi)
            #set to space and break if a dist exceeds the radius
            if math.sqrt((row-radius)**2 + (column-radius)**2) < radius:
                for key in data.keys():
                    if key != "radius":
                        cur_coord = data[key]
                        #print("x val: " + str(cur_coord[0]) + " y val: " + str(cur_coord[1]) + " \n")
                        if cur_angle > cur_coord[0] and cur_angle < cur_coord[1]:
                            print("hiiii")
                            pie_chart[row][column] = key

    [[print(pie_chart[r][c]) for r in range(2*radius)] for c in range(2*radius)]

def rectangular_to_polar(x_coord, y_coord):
   angle = math.atan2(y_coord, x_coord)
   radius = x_coord/math.cos(angle)
   return (radius, angle)

def polar_to_rectangular(radius, angle):
    return (radius*math.cos(angle), radius*math.sin(angle))

#from_pie_chart(3, [[" ", " ", "B", "A", " ", " "], [" ", "B", "B", "A", "A", " "], ["B", "B", "B", "A", "A", "A"], ["B", "B", "B", "C", "C", "C"], [" ", "B", "B", "C", "C", " "], [" ", " ", "B", "C", " ", " "]])
to_pie_chart({"radius": 5, "A": [0, math.pi/2], "B": [math.pi/2, -3*math.pi/2], "C": [-3*math.pi/2, 2*math.pi]})