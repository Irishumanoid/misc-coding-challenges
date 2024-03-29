[print(str(i) + "," + str(j)) for i in range(-1000, 1000) for j in range(-1000, 1000) if (i != 0 and j != 0 and i**2/j - j**2/i == 3*(2+1/(i*j)))]
