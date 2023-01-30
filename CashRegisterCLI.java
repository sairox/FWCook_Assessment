import java.util.Scanner;

public class CashRegisterCLI {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("Start java program");
        System.out.println("""
                This is a cash register, please enter your action:\s
                Actions include putting money, viewing how much money is in the register, taking certain bills and\040
                taking certain amount of change from the cash register\s
                Please enter what action (get, put, take, change or quit) then enter 5 digits with space in between.\s
                These digits represent different amounts of certain bills in the cash register (20s 10s 5s 2s 1s).""");

        String[] userInput;
        int[] changeArr = new int[5];
        boolean quit = false;

        while (!quit) {
            System.out.print("> ");
            userInput = actionInput(input);
            if(validAction(userInput)) {
                switch (userInput[0].toLowerCase()) {
                    case "get" -> ActionsHelper.getOperation(changeArr);
                    case "put" -> ActionsHelper.putOperation(userInput, changeArr);
                    case "take" -> ActionsHelper.takeOperation(userInput, changeArr);
                    case "change" -> ActionsHelper.changeOperation(userInput, changeArr);
                    case "quit" -> {
                        quit = true;
                        System.out.println("Bye");
                    }
                    default -> System.out.println("Please enter with get, put, take, change or quit only!");
                }
            } else{
                System.out.println("Please input only the action and 5 numbers or how much change you want");
            }
        }
        input.close();
    }

    private static String[] actionInput(Scanner input) {
        String action = input.nextLine();
        return action.split(" ");
    }

    private static boolean validAction(String[] userInput){
        return userInput.length == 6 || userInput.length == 1 || userInput.length == 2;
    }
}
