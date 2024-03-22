from functools import lru_cache
from timeit import repeat

@lru_cache
def num_stairs(stairs:int) -> int:
    if stairs == 1:
        return 1
    elif stairs == 2:
        return 2
    elif stairs == 3:
        return 7
    else:
        return num_stairs(stairs-1) + num_stairs(stairs-2) + num_stairs(stairs-3)
    

#print(num_stairs(30))
setup_code = "from __main__ import num_stairs"
stmt = "num_stairs(30)"
times = repeat(setup=setup_code, stmt=stmt, repeat=3, number=10)
print(f"Minimum execution time: {min(times)}")