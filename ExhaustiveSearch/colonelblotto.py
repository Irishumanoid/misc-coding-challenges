output = []

def get_distributions(cur_dist, round_num, objects, buckets):
    if buckets < 1:
        output.append(cur_dist)
        print(cur_dist)
        return
        
    if round_num == 0: 
        b = [0]*buckets
    else:
        b = cur_dist
    
    for i in range(objects+1):
        b[round_num] = i
        if buckets == 1:
            b[round_num] = objects
        get_distributions(b, round_num+1, objects-i, buckets-1)
   
    
def remove_duplicate_lists(list_of_lists):
    # Convert each inner list to a sorted tuple
    tuple_set = {tuple(sorted(lst)) for lst in list_of_lists}
    
    # Convert each tuple back to a list
    unique_list_of_lists = [list(tpl) for tpl in tuple_set]

    return unique_list_of_lists



get_distributions([], 0, 3, 5)
print(output)