import itertools

def get_index_of_val(input, val):
    for i in range(len(input)):
        if input[i] == val:
            return i
    return -1

def is_valid_permutation(current, permutation_vals):
    for i in range(len(current)):
        if permutation_vals[current[i]][0] == permutation_vals[current[(i+1) % len(current)]][0]:
            print('not valid permutation')
            return False
    print('valid permutation')
    return True


def match_existing(good_permutations, current, start, permutation_vals):
    if (len(good_permutations) == 0):
        return False
    for p in good_permutations:
        start = get_index_of_val(p, start)
        cur_start = get_index_of_val(current, start)
        for i in range(len(p)):
            print(permutation_vals[p[(start + i) % len(p)]])
            if permutation_vals[p[(start + i) % len(p)]] != permutation_vals[current[(cur_start + i) % len(current)]]:
                print('not a match')
                return False
    print('match exists')
    return True

permutations = list(itertools.permutations([i for i in range(4)]))
#ppl = ['a1', 'a2', 'a3', 'a4', 'b1', 'b2', 'b3', 'b4', 'c1', 'c2', 'c3', 'c4']
ppl=['a1', 'a2', 'b1', 'b2']

print(permutations)

good = []
for p in permutations:
    if is_valid_permutation(p, ppl) and not match_existing(good, p, 'a1', ppl):
        good.append(p)

print(f'number of valid  permutations is {len(good)}')
