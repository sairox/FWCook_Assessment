# FW Cook Assessment
### Cash Register CLI application

Instructions to run:
In terminal or command prompt, go to the path of the file and type this
command to compile the java class:

**Make sure your sdk is jdk17 as I used java 17 to make this project.**

>javac CashRegisterCLI.java

Then after compiling, run the program with this command:
>java CashRegisterCLI

The program will have a short prompt followed by > symbol requesting the user to make
an input.

Example inputs are 
> put 1 2 3 4 5 
> 
> get 
> 
> take 1 2 3 4 5
> 
> change 11

If you input non-numeric or invalid requests they will throw an error. Example bad inputs:
> put 1 2 3 4 r
> 
> take 1 2
> 
> null
> 
> change e
> 
> put -1 0 1 3 -4

Comments about the code:
- Global variables billsInRegister and total are used to store the values of the denomination of bills and total cash
while user in inputting the commands.
- I chose to use the Array data structure to store the bill denominations because I knew the exact amount of 
different bills there will be in the cash register. I knew that the 1st index in the array will represent 20 dollar
bills and 2nd index is 10 dollar bills and so on. Using a data structure where I had instant O(1) runtime access to 
the type of bill need is why I used an Array.
- The user input is put into an Array for the same reason as above. The validation makes sure that only 3 specific types
of inputs with a certain amount of numbers is allowed aka. put 1 2 3 4 5 or change 11 or get. (Validation for this is 
done in CashRegisterCLI.java on line 45 in validAction method).