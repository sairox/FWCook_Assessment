import java.util.Arrays;

public class ActionsHelper {

    //This is the object to store the bill denominations that are in the cash register
    private static final int[] billsInRegister = new int[5];
    //This is the object used to keep track of the total cash that is in the register
    private static int total;

    //Method that gets the amount of total cash and the different bill denominations
    //O(n) worst case
    public static void getOperation() {
        String formattedOutput = Arrays.toString(billsInRegister).replace(",", "")
                .replace("[", " ").replace("]", "");
        System.out.println("$" + total + formattedOutput);
    }

    //Method to put the money the user gives and incrementing the different denomination of bills
    //O(n) worst case
    public static void putOperation(String[] userInput) {
        int numOfBills;

        if (isNotNumericOrIsNegative(userInput)) {
            System.out.println("Please retry with all non-negative numeric inputs only after the action");
            return;
        }

        for (int i = 1; i < userInput.length; i++) {
            numOfBills = Integer.parseInt(userInput[i]);
            billsInRegister[i - 1] += numOfBills;
        }

        total = calculateTotalCash();
        getOperation();
    }

    //Method to take bills of certain denomination that the user requests for
    //O(n) worst case
    public static void takeOperation(String[] userInput) {
        if (isNotNumericOrIsNegative(userInput)) {
            System.out.println("Please retry with all non-negative numeric inputs only after the action");
            return;
        }

        if (notEnoughBills(userInput)) {
            System.out.println("Sorry please input a number of bills that are less than or equal to whats in the" +
                    "Cash Register!");
            return;
        }

        for (int i = 1; i < userInput.length; i++) {
            String num = userInput[i];
            int numOfBills = Integer.parseInt(num);

            switch (i) {
                case 1 -> billsInRegister[0] -= numOfBills;
                case 2 -> billsInRegister[1] -= numOfBills;
                case 3 -> billsInRegister[2] -= numOfBills;
                case 4 -> billsInRegister[3] -= numOfBills;
                case 5 -> billsInRegister[4] -= numOfBills;
            }
        }
        total = calculateTotalCash();
        getOperation();
    }

    //Method that gives the denomination of bills of a certain change amount the user requests
    //O(n), n being the user input amount of change
    public static void changeOperation(String[] userInput) {
        //Variable for the amount of change the user wants
        if (isNotNumericOrIsNegative(userInput)) {
            System.out.println("Please retry with all non-negative numeric inputs only after the action");
            return;
        }

        //Variable for the change amount user is requesting for
        int input = Integer.parseInt(userInput[1]);

        if (input <= total) {

            //Variables for storing the cash denominations that is being provided to the user
            int twenty = 0, ten = 0, five = 0, two = 0, one = 0;

            //Boolean variable for when the change is successfully collected from the cash register.
            boolean success = true;

            //Loop for finding denomination of bills
            while (input > 0) {
                //If input variable is more than or equal to $20 and 20's are available in the cash register
                if (input >= 20 && billsInRegister[0] - twenty > 0) {
                    //Deduct $20 from input variable and increment twenty variable by one
                    input -= 20;
                    twenty++;
                }
                //If input variable is more than or equal to $10 and 10's are available in the cash register
                else if (input >= 10 && billsInRegister[1] - ten > 0) {
                    //Deduct $10 from input variable and increment ten variable by one
                    input -= 10;
                    ten++;
                }
                //If input variable is more than or equal to $5 and 5's are available in the cash register
                else if (input >= 5 && billsInRegister[2] - five > 0) {
                    //Deduct $5 from input variable and increment five variable by one
                    input -= 5;
                    five++;
                }
                //If input variable is more than or equal to $2 and 2's are available in the cash register
                else if (input >= 2 && billsInRegister[3] - two > 0) {
                    //Deduct $2 from input variable and increment two variable by one
                    input -= 2;
                    two++;
                }
                //If input variable is more than or equal to $1 and 1's are available in the cash register
                else if (billsInRegister[4] - one > 0) {
                    //Deduct $1 from input variable and increment one variable by one
                    input -= 1;
                    one++;
                } else {
                    System.out.println("Sorry there is not enough cash in the register for this amount");
                    //success is set to false if bills are not there to equal to the change amount
                    success = false;
                    break;
                }
            }
            //If there is enough change in the cash register, then success remains true, and we deduct the
            //denomination of bills from the register
            if (success) {
                //deduct found change cash from register cash
                billsInRegister[0] -= twenty;
                billsInRegister[1] -= ten;
                billsInRegister[2] -= five;
                billsInRegister[3] -= two;
                billsInRegister[4] -= one;

                total = calculateTotalCash();
                //Printing the denomination of bills that is being taken after removing from the cash register
                System.out.println(twenty + " " + ten + " " + five + " " + two + " " + one);
            }
        } else {
            System.out.println("Sorry, please input an amount smaller than what is in the register");
        }
    }

    //This method checks if the user inputted a numeric number for a denomination by parsing the userInput which comes
    //in as a String and if it catches an exception and returns true that means the user inputted a non-numeric number
    //if no exception is thrown then it checks if user inputted a negative number, which is not allowed also
    private static boolean isNotNumericOrIsNegative(String[] userInput) {
        int bill;
        for (int i = 1; i < userInput.length; i++) {
            try {
                bill = Integer.parseInt(userInput[i]);
                if (bill < 0) {
                    return true;
                }
            } catch (NumberFormatException e) {
                return true;
            }
        }

        return false;
    }

    //This method which is used in the take operation checks to see if the denomination of a bill the user requested
    //is more than what is in the cash register. Ex. if there is only 1 20 dollar bill and user requests 2 bills
    //the method will return true, or else it returns false
    private static boolean notEnoughBills(String[] userInput) {
        for (int i = 1; i < userInput.length; i++) {
            if (Integer.parseInt(userInput[i]) > ActionsHelper.billsInRegister[i - 1]) {
                return true;
            }
        }
        return false;
    }

    //This is the method that calculates how much total cash is in the cash register by multiplying all the denominations
    //by their value in the billsInRegister array
    private static int calculateTotalCash() {
        int total = 0;

        total += ActionsHelper.billsInRegister[0] * 20;
        total += ActionsHelper.billsInRegister[1] * 10;
        total += ActionsHelper.billsInRegister[2] * 5;
        total += ActionsHelper.billsInRegister[3] * 2;
        total += ActionsHelper.billsInRegister[4];

        return total;
    }
}
