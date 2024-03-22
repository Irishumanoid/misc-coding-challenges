def sum(arr):
    total = 0
    if type(arr) == int:
        return arr
    else:
        for i in range(len(arr)):
            if type(arr[i]) == list:
                total += sum(arr[i])
            else:
                total += arr[i]
        return total

def element_swap(arr, i, j):
        max = arr[i]
        arr[i] = arr[j]
        arr[j] = max

#make sure there is adequate coverage
def sort(arr):
    for i in range(len(arr)-1):
        for j in range(i+1, len(arr)):
            if sum(arr[i]) > sum(arr[j]):
                if type(arr[i]) == list:
                    sort(arr[i])
                    element_swap(arr, i, j)
                elif type(arr[j]) == list:
                    sort(arr[j])
                    element_swap(arr, i, j)
                else:
                    element_swap(arr, i, j)
    return arr

def main():
    print(sort([[2, 1], [[7, 6], 5, 1], 2, 1]))

if __name__ == '__main__':
    main()
