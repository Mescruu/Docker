import java.util.Scanner;

public class Lab4 {

    public static void main(String[] args) {
        System.out.println(" Welcome to Java Calculator v0.1 \n");
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n Please enter two numbers");
        System.out.print("\n First number: ");
        int firstNumber = scanner.nextInt();
        System.out.print("\n Second number: ");
        int secondNumber = scanner.nextInt();
        System.out.println("\n Select between (*,/,+,-)\n Type out the character in a single letter: ");
        String operation = scanner.next();

        String eo = "You have selected ";

        switch (operation) {
        case "*":
            System.out.println(eo + "* \n Your Result: "+ ( firstNumber * secondNumber ));
            break;
        case "/":
            System.out.println(eo + "/ \n Your Result: "+ ( firstNumber / secondNumber ));
            break;
        case "+":
            System.out.println(eo + "+ \n Your Result: "+ ( firstNumber + secondNumber ));
            break;
        case "-":
            System.out.println(eo + "- \n Your Result: "+ ( firstNumber - secondNumber ));
            break;
        default: System.out.println("\n Please select a valid character");
        }

        scanner.close();
        System.out.println(" Closing Application ");
    }
}