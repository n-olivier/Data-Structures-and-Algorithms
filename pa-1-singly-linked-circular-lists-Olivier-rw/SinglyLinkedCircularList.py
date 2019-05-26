# Olivier Nshimiyimana | Cohort 2
# PART II - Singly Linked Circular List

"""
    The program below uses class List to create a circular singly linked list,
    class List store its data by creating a node for each using Node class.

    Class List has some different methods for performing basic operations for the list.
    Those are 'inserting', 'searching', 'deleting', 'displaying', and 'step' for stepping to the next node.

"""


class Node:
    """
        This class simply help us to create objects which holds two things: its data and reference to its neighbour.
    """

    def __init__(self, data):
        self.data = data
        self.nextRef = None


class SLCList:
    """
        This class create our circular singly linked list.
    """

    def __init__(self, data=None):
        """
        :param data: Int value or data to be hold by our Node object.

        When initialized without parameter current variable points None.
        When initialized with a parameter, it creates a Node object pointing to itself as next reference.

        """

        if data is None:
            self.current = None
        else:
            initial_link = Node(data)
            initial_link.nextRef = initial_link

            self.current = initial_link

    def insert(self, data=None):
        """
        :param data: Int value or data to be inserted in a list

        It creates a Node object and points its next reference where 'current' object was pointing,
        and then change the next reference of 'current' to the address of that new object created.
        """

        if self.current is None:
            self.__init__(data)

        else:
            new_node = Node(data)

            new_node.nextRef = self.current.nextRef
            self.current.nextRef = new_node
            self.current = new_node

    #     Space complexity: O(1), Time complexity: O(1)

    def search(self, data=None):
        """
        :param data: Int value to search
        :return: Reference for searched data or None if it does not exist in a list

        It uses a pointer which starts from current's next reference and ends at current.
        Over the loop it compares the data of 'pointer' with parameter given,
        returns its reference when found and returns None at the end of function when not found.
        """

        if self.current.data == data:
            return self.current

        pointer = self.current.nextRef

        while pointer != self.current:
            if pointer.data == data:
                return pointer
            pointer = pointer.nextRef

        return None

    #     Space complexity: O(1), Time complexity: O(n)

    def delete(self, data=None):
        """

        :param data: Int value to delete
        :return: Reference for deleted data or None if it does not exist in a list

        It uses a pointer which starts from current's next reference and ends at current.
        Over the loop it compares the data of 'pointer' with parameter given,
        delete it and returns its reference when found and returns None at the end of function when not found.
        """

        pointer = self.current.nextRef
        prev_ref = self.current

        while pointer != self.current:
            if pointer.data == data:
                prev_ref.nextRef = pointer.nextRef
                return pointer

            prev_ref = pointer
            pointer = pointer.nextRef

        if pointer.data == data:
            prev_ref.nextRef = pointer.nextRef
            self.current = self.current.nextRef
            return pointer

        return None

    #     Space complexity: O(1), Time complexity: O(n)

    def step(self):
        """
        Gives current its next reference as its current reference
        """

        self.current = self.current.nextRef

    #     Space complexity: O(1), Time complexity: O(1)

    def display(self):
        """
        Uses a pointer which starts from current's next reference and ends at current.
        Over the loop it prints the data of 'pointer'
        """

        print(self.current.data)

        pointer = self.current.nextRef

        while pointer != self.current:

            print(pointer.data)

            pointer = pointer.nextRef

    #     Space complexity: O(1), Time complexity: O(n)


"""
Resources used:

1.  Python - Linked Lists
    www.tutorialspoint.com. (2019). Python - Linked Lists. 
    [online] Available at: https://www.tutorialspoint.com/python/python_linked_lists.htm 
    [Accessed 27 Jan. 2019].
    
2.  Circular Singly Linked List | Insertion - GeeksforGeeks
    GeeksforGeeks. (2017). Circular Singly Linked List | Insertion - GeeksforGeeks. 
    [online] Available at: https://www.geeksforgeeks.org/circular-singly-linked-list-insertion/ 
    [Accessed 28 Jan. 2019].
"""
