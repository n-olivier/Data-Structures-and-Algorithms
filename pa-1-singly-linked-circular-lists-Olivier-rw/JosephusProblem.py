# Olivier Nshimiyimana | Cohort 2
# PART III - The Josephus Problem

"""
    Given the number of people, number of steps to jump(k), and the starting position, this function
    returns an initial position where the last person was on.
"""

from SinglyLinkedCircularList import SLCList


def josephus_problem(number, k, pos=1):
    """
    Let's start by creating a circular list object using our SLCList class.
    And insert people using its insert method.
    """

    people_in_circle = SLCList()
    for i in range(number):
        people_in_circle.insert((i+pos) % number)               # Note: The algorithm indicate the last person by '0'

    # Now we will be killing one by one as we store them in a list
    killed = []

    iterator = 0

    while iterator < number:

        for i in range(k):                                      # Moving 'k' steps
            people_in_circle.step()

        sword = people_in_circle.current.nextRef.data           # After counting we put our sword on person next

        killed.append(people_in_circle.delete(sword).data)      # Kill te person as we append him to the list

        iterator = iterator + 1

    # Note: Last person standing (or the safe position) is the last item in the list.
    return killed

    # Space complexity: O(n), Time complexity: O(n.k)


"""
Resources used:

1.  Josephus problem | Set 1 (A O(n) Solution) - GeeksforGeeks
    GeeksforGeeks. (2012). Josephus problem | Set 1 (A O(n) Solution) - GeeksforGeeks. 
    [online] Available at: https://www.geeksforgeeks.org/josephus-problem-set-1-a-on-solution/ 
    [Accessed 27 Jan. 2019].

"""
