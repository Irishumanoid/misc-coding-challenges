import itertools
import numpy as np


def choose_function(n, k):
    if k == 0 or n == k:
        return 1
    else: 
        return choose_function(n-1, k-1) + choose_function(n-1, k)

def find_subsets(parent, subset_size):
    return [set(i) for i in itertools.combinations(parent, subset_size)]

def get_choices(num):
    choices = find_subsets(num, num/2)
    prisoners = set(range(1, num+1))

    #assign each prisoner to a subset, iterating over all possible combinations
