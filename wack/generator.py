def gen_seq():
    counter = 0
    while True:
        print(counter)
        yield counter #returns generator object
        counter += 1

def is_palindrome(num):
    if num // 10 == 0:
        return False
    
    updater = num
    reversed = 0

    while updater != 0:
        reversed = reversed*10 + updater%10
        updater = updater // 10

    if reversed == num:
        return True
    else: 
        return False
    

def get_palindromes():
    counter = 0
    while True:
        if is_palindrome(counter):
            i = (yield counter) #assign yielded value from generator to i
            if i is not None:
                counter = i
        counter += 1


generator = get_palindromes()
for i in generator:
    digits = len(str(i))
    if digits > 5:
        generator.close()
    generator.send(10**digits)
    print(i)


'''
file_name = "techcrunch.csv"
lines = (line for line in open(file_name)) # use () instead of [] for generators
list_line = (s.rstrip().split(",") for s in lines)
cols = next(list_line)
company_dicts = (dict(zip(cols, data)) for data in list_line)
funding = (int(company_dict["raisedAmt"]) for company_dict in company_dicts if company_dict["round"] == "a")
total_series_a = sum(funding)
average_series_a = total_series_a/len(funding)

print(f"Total series A fundraising: ${total_series_a}")'''
