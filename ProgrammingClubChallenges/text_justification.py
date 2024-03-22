import math

def format(words: [str], maxWidth: int):
    lines = [[]]
    final_lines = []

    for i in range(len(words)):
        last_row = lines[len(lines)-1]
        total = get_arr_sum(last_row)
        if total == 0 or total + len(words[i]) + 1 < maxWidth:
            lines[len(lines)-1].append(words[i])
        else:
            lines.append([words[i]])


    for i in range(len(lines)):
        cur_row = lines[i]
        num_words = len(cur_row)
        total = get_arr_sum(cur_row)
        spaces = maxWidth - total

        if spaces == 0 or num_words == 1:
            final_lines.append(cur_row[0] + " "*spaces)
        else:
            final_row = ""
            buffer = math.floor(spaces/(num_words-1))
            num_extra = spaces % (num_words-1)
            for val in cur_row:
                final_row += val + " "*buffer
                if num_extra > 0:
                    final_row += " "
                    num_extra -= 1
            final_lines.append(final_row)

    [print(l) for l in final_lines]
            

def get_arr_sum(arr: list):
    return sum(len(entry) for entry in arr)



    
