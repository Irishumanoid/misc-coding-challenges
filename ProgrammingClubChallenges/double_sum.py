import math

def choose_function(n, k):
    return factorial(n) / (factorial(n - k) * factorial(k))

def factorial(n: int) -> int:
    if n > 0:
        return n * factorial(n - 1)
    else:
        return 1

print(choose_function(10, 9))

init_sum = 0
for i in range(10):
    for j in range(10):
        init_sum += choose_function(i+j, j)*choose_function(20-i-j, 10-j)
print("solution to part of SMT problem is: " + str(init_sum))

counter = 0
for i in range(1, 21):
    counter += math.floor(i**3 - 30*i**2 + 198*i + 2024)
print(counter)

#Find the number of positive integer pairs (x,y) that satisfy √x≥y and x≤2024.
counter = 0
for i in range(1, 2025):
    counter += math.floor(math.sqrt(i))
print(counter)

#Find the number of positive integer pairs (x,y) that satisfy 5x/6≥y and x<360.
counter = 0
for i in range(1, 360):
    counter += math.floor(5*i/6)
print(counter)