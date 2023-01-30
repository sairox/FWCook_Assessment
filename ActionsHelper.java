import java.util.Arrays;

public class ActionsHelper {

    //This is the object used to keep track of the total cash that is in the register
    private static int total;

    public static void getOperation(int[] billsInRegister) {
        String formattedOutput = Arrays.toString(billsInRegister).replace(",", "")
                .replace("[", " ").replace("]", "");
        System.out.println("$" + total + formattedOutput);
    }

    public static void putOperation(String[] userInput, int[] billsInRegister) {
        int numOfBills;

        if (!isNumeric(userInput)) {
            System.out.println("Please retry with all numeric inputs only after the action");
            return;
        }

        for (int i = 1; i < userInput.length; i++) {
            numOfBills = Integer.parseInt(userInput[i]);
            billsInRegister[i - 1] += numOfBills;
        }

        total = calculateTotal(billsInRegister);
        getOperation(billsInRegister);
    }

    public static void takeOperation(String[] userInput, int[] billsInRegister) {
        if (!isNumeric(userInput)) {
            System.out.println("Please retry with all numeric inputs only after the action");
            return;
        }

        if (checkEnoughBills(userInput, billsInRegister)) {
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

        total = calculateTotal(billsInRegister);
        getOperation(billsInRegister);
    }

    public static void changeOperation(String[] userInput, int[] billsInRegister) {
        //Variable for the amount fof change the user wants
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
                                    System.out.println("Sorry");
                                    success = false;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            //if found
            if (success) {
                //deduct found change cash from register cash
                billsInRegister[0] -= twenty;
                billsInRegister[1] -= ten;
                billsInRegister[2] -= five;
                billsInRegister[3] -= two;
                billsInRegister[4] -= one;

                total = calculateTotal(billsInRegister);
                System.out.println(twenty + " " + ten + " " + five + " " + two + " " + one);
            }
        }
        else {
            System.out.println("Sorry, please input an amount smaller than what is in the register");
        }
    }

    //This method checks if the user inputted a numeric number for a denomination
    private static boolean isNumeric(String[] userInput) {
        for (int i = 1; i < userInput.length; i++) {
            try {
                Double.parseDouble(userInput[i]);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    //Rename method
    private static boolean checkEnoughBills(String[] userInput, int[] billsInRegister) {
        int counter = 0;
        for (int i = 1; i < userInput.length; i++) {
            if (Integer.parseInt(userInput[i]) > billsInRegister[i - 1]) {
                counter++;
            }
        }
        return counter > 0;
    }

    //This is the method that calculates how much total cash is in the cash register
    private static int calculateTotal(int[] billsInRegister) {
        int total = 0;

        total += billsInRegister[0] * 20;
        total += billsInRegister[1] * 10;
        total += billsInRegister[2] * 5;
        total += billsInRegister[3] * 2;
        total += billsInRegister[4];

        return total;
    }
}
