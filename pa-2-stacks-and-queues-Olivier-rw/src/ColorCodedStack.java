/*
    This program implement a two stacks array, with two pointers which start from both ends of the array.
    You can implement it with a certain size of array, and default is 52 if not specified.
 */

public class ColorCodedStack {
//    initializing global variables for our stack
    int redTop;
    int blackTop;
    String [] Stack;

//    A constructor when size of stack is given
    public ColorCodedStack(int size){
        Stack = new String[size];
        redTop = 0;
        blackTop = Stack.length;
    }

//    A constructor with default size
    public ColorCodedStack(){
        Stack = new String[52];
        redTop = 0;
        blackTop = Stack.length;
    }

//    Method for pushing a black card
    public void blackPush(String data){
        if (redTop < blackTop){
            blackTop--;
            Stack[blackTop] = data;
        }
        else {
            System.out.println("Stack Overflow");
        }
    }

//    Method for pushing a red card
    public void redPush(String data){
        if (blackTop > redTop){
            Stack[redTop] = data;
            redTop++;
        }
        else {
            System.out.println("Stack Overflow");
        }
    }

//    Method for popping a black card
    public String blackPop(){
        if (blackTop > 52){
            String temp = Stack[blackTop];
            Stack[blackTop] = null;
            blackTop++;
            return temp;
        }
        else {
            return "Out of bound";
        }
    }

//    Method for peeking a black card
    public String blackPeek(){
        return Stack[blackTop];
    }

//    Method for popping a red card
    public String redPop(){
        if (redTop > 0){
            String temp = Stack[redTop-1];
            Stack[redTop-1] = null;
            redTop++;
            return temp;
        }
        else {
            return "Out of bound";
        }
    }


//    Method for peeking a black card
    public String redPeek(){
        return Stack[redTop-1];
    }

//    This method returns the length of a stack
    public int size(){
        return blackSize() + redSize();
    }

//    This method returns number of black items in a stack
    public int blackSize(){
        return Stack.length - blackTop;
    }

//    This method returns number of red items in a stack
    public int redSize(){
        return redTop;
    }

//    Method for searching an item in a Stack
    public int search(String data){
        for (int iterator=0; iterator< redTop; iterator++){
            if (Stack[iterator].equals(data)){
                return iterator;
            }
        }
        for (int iterator=blackTop; iterator< Stack.length; iterator++){
            if (Stack[iterator].equals(data)){
                return iterator;
            }
        }
        return -1;
    }

//    Method for display the Stack
    public void display(){
        for (int iterator=0; iterator< redTop; iterator++){
            System.out.println(Stack[iterator]);
        }
        for (int iterator=blackTop; iterator< Stack.length; iterator++){
            System.out.println(Stack[iterator]);
        }
    }

    public static void main(String[] args) {

    }
}

/*
    display() and search() methods of this stack uses runtime complexity of O(n) in the worst case scenario.
    All other methods uses O(1) runtime.

    And the whole program uses O(n) space.
 */