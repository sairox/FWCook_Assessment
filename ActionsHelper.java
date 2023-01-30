import java.util.Arrays;

public class ActionsHelper {

    //This is the object used to keep track of the total cash that is in the register
    private static int total;

    //This is the object to store the bill denominations that are in the cash register
    private static final int[] billsInRegister = new int[5];

    //Method that gets the amount of total cash and the different bill denominations
    public static void getOperation() {
        String formattedOutput = Arrays.toString(billsInRegister).replace(",", "")
                .replace("[", " ").replace("]", "");
        System.out.println("$" + total + formattedOutput);
    }

    //Method to put the money the user gives and incrementing the different denomination of bills
    public static void putOperation(String[] userInput) {
        int numOfBills;

        if (isNotNumeric(userInput)) {
            System.out.println("Please retry with all numeric inputs only after the action");
            return;
        }

        for (int i = 1; i < userInput.length; i++) {
            numOfBills = Integer.parseInt(userInput[i]);
            billsInRegister[i - 1] += numOfBills;
        }

        total = calculateTotal();
        getOperation();
    }

    //Method to take bills of certain denomination that the user requests for
    public static void takeOperation(String[] userInput) {
        if (isNotNumeric(userInput)) {
            System.out.println("Please retry with all numeric inputs only after the action");
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
        total = calculateTotal();
        getOperation();

        total = calculateTotal(billsInRegister);
        getOperation(billsInRegister);
    }

    //Method that gives the denomination of bills of a certain change amount the user requests
    public static void changeOperation(String[] userInput) {
        //Variable for the amount of change the user wants
        if(isNotNumeric(userInput)){
            System.out.println("Please retry with all numeric inputs only after the action");
            return;
        }

        int money = Integer.parseInt(userInput[1]);
        if (money <= total) {
            //create variables for storing the cash that is being provided to the user
            int twenty = 0;
            int ten = 0;
            int five = 0;
            int two = 0;
            int one = 0;
            //boolean variable for when the change is successfully collected from the cash register.
            boolean success = true;
            //loop for finding change
            while (money > 0) {
                //if user wants more than or equal to $20 and 20's are available in the cash register
                if (money >= 20 && billsInRegister[0] - twenty > 0) {
                    //deduct $20 from user request and increment twenty variable by one
                    money -= 20;
                    twenty++;
                } //if user wants more than or equal to $10 and 10's are available in the cash register
                else if (money >= 10 && billsInRegister[1] - ten > 0) {
                    //deduct $10 from user request and increment ten variable by one
                    money -= 10;
                    ten++;
                } else {
                    //if user wants more than or equal to $10 and cash user
                    //wants mod 2 is 0 and more than one $5 bills is available in the cash register
                    if (money >= 10 && money % 2 == 0 && billsInRegister[2] - five > 1) {
                        //deduct $10 from user request and increment five variable by two
                        money -= 10;
                        five += 2;
                    } else {
                        //if user wants more than $10 and 5 bills are available in the cash register
                        if (money > 10 && billsInRegister[2] - five > 0) {
                            //deduct $5 from user request and increment five variable by one
                            money -= 5;
                            five++;
                        } else {
                            //if user request is more than $5 and cash is available in 5 and 1 bills
                            if (money > 5 && billsInRegister[2] - five > 0 && billsInRegister[4] - one > 0) {
                                //deduct $5 from user request and increment five by one
                                money -= 5;
                                five++;
                            } else {
                                //if user wants more than or equal to $2 and 2 bills available in the cash register
                                if (money >= 2 && billsInRegister[3] - two > 0) {
                                    //deduct $2 from user request and increment two variable by one
                                    money -= 2;
                                    two++;
                                } //if user wants more than or equal to $1 and 1 bills are available in the cash register
                                else if (billsInRegister[4] - one > 0) {
                                    //deduct $1 from user request and increment one variable by one
                                    money -= 1;
                                    one++;
                                } else {
                                    //print sorry message, set success to false(not found) and break the loop
                                    System.out.println("Sorry there is not enough cash in the register for this amount");
                                    success = false;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            //if there is enough change in the cash register, then success remains true, and we deduct the cash from the
            //register
            if (success) {
                //deduct found change cash from register cash
                billsInRegister[0] -= twenty;
                billsInRegister[1] -= ten;
                billsInRegister[2] -= five;
                billsInRegister[3] -= two;
                billsInRegister[4] -= one;

                total = calculateTotal();
                System.out.println(twenty + " " + ten + " " + five + " " + two + " " + one);
            }
        }
        else {
            System.out.println("Sorry, please input an amount smaller than what is in the register");
        }
    }

    //This method checks if the user inputted a numeric number for a denomination by parsing the userInput which comes
    //in as a String and if it catches an exception and returns true that means the user inputted a non numeric number
    private static boolean isNotNumeric(String[] userInput) {
        for (int i = 1; i < userInput.length; i++) {
            try {
                Integer.parseInt(userInput[i]);
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
    private static int calculateTotal() {
        int total = 0;

        total += ActionsHelper.billsInRegister[0] * 20;
        total += ActionsHelper.billsInRegister[1] * 10;
        total += ActionsHelper.billsInRegister[2] * 5;
        total += ActionsHelper.billsInRegister[3] * 2;
        total += ActionsHelper.billsInRegister[4];

        return total;
    }
}
