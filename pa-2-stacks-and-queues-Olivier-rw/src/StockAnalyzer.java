/*
    This program implements a stock using Queue built in library
 */
import java.util.InputMismatchException;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

public class StockAnalyzer {
    Queue<Integer> Stock = new LinkedList<>();
    int Profit;
    int stockSize=0;

//    This method adds all purchased shares in a queue by their prices
    public void purchaseShares(Integer pricePerShare, int numberOfShares){

        for (int iterator=0; iterator < numberOfShares; iterator++){
            Stock.add(pricePerShare);
            stockSize++;
        }
    }

//    This method removes all sold shares in a queue by their prices
    public void sellShares(Integer pricePerShare, int numberOfShares){
        if (stockSize >= numberOfShares){
            for (int iterator=0; iterator < numberOfShares; iterator++){

                int purchasePrice = Stock.remove();
                Profit += (pricePerShare - purchasePrice);      // As we remove from queue, we add it to our profit.
                stockSize--;
            }
            System.out.println("Profit after selling is:" + Profit);
        }
        else {
            System.out.println("There are no enough shares to sell");
        }

    }

//    This function perform operations on the queue depending on user commands
    public void stockOperations(String operations, int numberOfShares, int pricePerShare){
        if (operations.equals("purchase")){
            purchaseShares(pricePerShare, numberOfShares);
        }
        else if (operations.equals("sell")){
            sellShares(pricePerShare, numberOfShares);
        }
    }

    public static void main(String[] args) {
//        Initialisation of useful variables
        Scanner scanner = new Scanner(System.in);
        String[] input = new String[6];
        StockAnalyzer AlgoStock = new StockAnalyzer();


//        Welcome message to the app
        System.out.println("Welcome to Stock Analyzer app, you can start recording your transactions now");
        System.out.println("Please use the purchase and sell commands right way in this format");
        System.out.println("'purchase 20 shares at $30 each' for purchasing shares or");
        System.out.println("'sell 20 shares at $30 each' for selling shares.");

        while (true){
            System.out.println("Record a transaction:");
            try {
                String reader = scanner.nextLine();

//                Splitting user commands
                input = reader.split(" ");

                if (input.length == 6){
                    try{

//                        Getting number of shares from user command
                        int numberOfShares = Integer.parseInt(input[1]);

//                        Getting price per share from user command
                        int pricePerShare = Integer.parseInt(input[4].replace("$", ""));

//                        Getting type of operation to do from user command
                        String operation = input[0];

                        AlgoStock.stockOperations(operation, numberOfShares, pricePerShare);
                    }
                    catch (NumberFormatException e){
                        System.err.println("Invalid input! Please make sure you provide the right input");
                    }

                    }
                else {
                    System.out.println("Can't perform the transaction. Please make sure you provide the right input");
                }

                System.out.println("Do you want to continue with more transactions: Y/N");

                if (scanner.nextLine().equals("N")){
                    System.out.println("Profit after all transactions = " + AlgoStock.Profit);
                    return;
                }


            }
            catch (InputMismatchException e){
                System.err.println("Please check your inputs again. Your input should be in either of these formats:\n");
                System.err.println("'purchase 20 shares at $30 each' for purchasing shares or \n");
                System.err.println("'sell 20 shares at $30 each' for selling shares.");
            }
        }
    }
}

/*
    Queue library used in this program uses runtime complexity of O(1) for each of the operations our user can perform.
    And the whole program uses space complexity of O(n)
 */