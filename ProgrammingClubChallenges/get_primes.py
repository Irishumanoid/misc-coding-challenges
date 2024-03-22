import math

def get_primes(input: str):
    counter = 0
    primes = []
    cur_nums = []
    while counter < len(input):
        if input[counter].isnumeric():
            cur_nums.append(int(input[counter]))
        elif not input[counter].isnumeric() or counter == len(input)-1:
            num = 0;

            for i in range(len(cur_nums)):
                num += cur_nums[i]*10**(len(cur_nums)-i-1)
            cur_nums = []

            if is_prime(num):
                primes.append(num)

        counter += 1

    for prime in primes:
        print(prime)

def is_prime(num) -> bool: 
    if num == 0 or num == 1:
        return False
    for i in range(2, math.floor(math.sqrt(num))+1):
        if num % i == 0:
            return False
    return True
        
get_primes("hello7h97.^")