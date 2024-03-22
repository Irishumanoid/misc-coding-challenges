def copy_n_times(string_val: str, n: int):
    counter = n
    final = ""
    while counter > 0:
        final += string_val
        counter -= 1

    return final


def get_mult_table(n: int):
    lines = []
    for i in range(1, n+1):
        cur_sub = []
        for j in range(1, n+1):
            cur_sub.append(i*j)
        lines.append(cur_sub)

    col_length = len(lines[0])
    last_entries = [line[col_length-1] for line in lines]
    sizes = [len(str(i)) for i in last_entries]

    total = ""
    for row in lines:
        row_total = " "
        for entry in row:
            row_total += copy_n_times("-", sizes[row.index(entry)]) + "+"
        row_total += "\n"

        row_total += "".join(["|" + str(entry) + str(copy_n_times(" ", sizes[row.index(entry)]-len(str(entry)))) for entry in row])
        row_total += "\n"

        total += row_total

    return total

print(get_mult_table(10))

