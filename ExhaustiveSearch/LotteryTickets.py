import itertools
import numpy as np

#N: reduced set, R: number on ticket, J: number guaranteed on winning ticket, L: number to win common to both tickets
#fix NcJ subsets covered with a given set of tickets

#instead of covering NcJ subsets in terms of JcL subsets of NcJ items
#if the intersection of an NcJ item with a ticket >= J, at least one item from its JcL subset is covered 

#to get a lower bound on # tickets, how many NcJ subsets have coverage at least L with any given NcJ subset?
#sum_{i=L}^{J} (R c i) (N-R c J-i) 

vector_2d  = []
vector_3d = []

def get_minimum_lottery_cover(N, R, J, L):
    subsets_to_cover = find_subsets(N, L)
   


def choose_function(n, k):
    if k == 0 or n == k:
        return 1
    else: 
        return choose_function(n-1, k-1) + choose_function(n-1, k)
    

def find_subsets(parent, subset_size):
    return [set(i) for i in itertools.combinations(parent, subset_size)]

def main():
    print(choose_function(6, 3))
    print(find_subsets({1,2,3,4,5}, 3))

if __name__ == "__main__":
    main()