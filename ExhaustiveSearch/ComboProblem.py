import math
def factorial(n):
    if n > 1:
        return n*factorial(n-1)
    else:
        return 1
    
def choose_function(n, k):
    if k == 0 or n == k:
        return 1
    else: 
        return choose_function(n-1, k-1) + choose_function(n-1, k)
    

chi_sqrd = 0
for i in range(1, 30):
    prob = 0
    for j in range(i-1):
        prob += choose_function(i-2,j)*(j+2)
    prob = prob/factorial(i)
    print(prob)
    chi_sqrd += (5.325*math.pow(10,-8) - prob)**2/(5.325*math.pow(10,-8))
print("chi squared value is: " + str(chi_sqrd))
