#stacks, queues, and lists
def parenthesis_checker(input: str):
    counter = 0
    for i in range(len(input)):
        if input[i] == '(':
            counter += 1
        elif input[i] == ')':
            counter -= 1
        else:
            return 'invalid input'
        
        if counter < 0:
            return f'not properly nested at index {i}'
    if counter != 0:
        return 'not properly balanced'
    else:
        return 'properly balanced'

#length of longest balanced parentheses (not necessarily continguous)
def calculate_longest_balanced(input: str):
    prev_indices = []
    num_pairs = 0
    for i in range(len(input)):
        if input[i] == '(':
            for j in range(i+1, len(input)):
                if input[j] == ')' and not prev_indices.__contains__(j):
                    prev_indices.append(j)
                    num_pairs += 1
                    break
    return num_pairs * 2
  

#linear time algorithm to reverse direction of singly linked list
class Node:
    def __init__(self, data):
        self.data = data
        self.next = None #pointer to next node

class LinkedList:
    def __init__(self):
        self.head = None
    
    def insert_at_start(self, data):
        new_node = Node(data)
        if self.head == None:
            self.head = new_node
        else:
            new_node.next = self.head
            self.head = new_node
    
    def insert_at_index(self, data, index):
        if index == 0:
            self.insert_at_start(data)
        else:
            cur_node = self.head
            position = 0
            while position+1 != index and cur_node != None:
                cur_node = cur_node.next
                position += 1
            if cur_node != None: 
                new_node = Node(data)
                if cur_node.next != None:
                    new_node.next = cur_node.next
                cur_node.next = new_node
            else:
                print('Node not present')

    def delete_at_index(self, index):
        if index == 0:
            self.head = self.head.next
        else:
            cur_node = self.head
            position = 0
            while position+1 != index and cur_node != None:
                cur_node = cur_node.next
                position += 1
            if cur_node != None:
                next_node = cur_node.next
                if next_node.next:
                    data = cur_node.next.data
                    cur_node.next = next_node.next
                    next_node.next = None
                    return data
                else: 
                    cur_node.next = None
            else:
                print('Node not present')
                
    
    def print_list(self):
        cur_node = self.head
        while cur_node:
            print(cur_node.data)
            cur_node = cur_node.next
    
    def reverse_list_dir(self):
        nodes = []
        cur_node = self.head
        while cur_node:
            nodes.append(cur_node)
            cur_node = cur_node.next
        
        rev_list = LinkedList()
        for i in range(len(nodes)):
            rev_list.insert_at_start(nodes[i].data)
        self.head = rev_list.head
    
    def get_size(self):
        position = 0
        cur_node = self.head
        while self.head != None:
            position += 1
            cur_node = cur_node.next
        

#stack with constant time push, pop, and findmin
class Stack:
    def __init__(self):
        self.size = 0
        self.stack = LinkedList()
        self.min_stack = LinkedList()
    
    def push(self, data):
        node = Node(data)
        node.next = self.stack.head
        self.stack.insert_at_start(node.data)
        if self.min_stack.head == None or (self.min_stack.head and data <= self.min_stack.head.data):
            self.min_stack.insert_at_start(data)
        self.size += 1
    
    def pop(self):
        out = self.stack.head.data
        self.stack.delete_at_index(0)
        if self.min_stack.head and out <= self.min_stack.head.data:
            self.min_stack.delete_at_index(0)
        self.size -= 1
    
    def find_min(self):
        return self.min_stack.head.data

    def print_stack(self):
        node = self.stack.head
        while node != None:
            print(node.data)
            node = node.next
