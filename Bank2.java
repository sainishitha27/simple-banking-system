import java.util.Scanner;

public class Bank2 {
  
     static double bal = 0.0;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opt;
        while (true) {
            System.out.println("Welcome");
            System.out.println("1. Check balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Enter any option: ");
            opt = sc.nextInt();

            switch (opt) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    exit();
                    return; 
                default:
                    System.out.println("Entered invalid option. Try again.");
                    break;
            }
        }
    }

    public static void checkBalance() {
        System.out.println("Your current balance is " + bal);
    }

    public static void deposit() {
        System.out.print("Enter the amount to deposit: ");
        double amt = sc.nextDouble();
        if (amt <= 0) {
            System.out.println("Invalid deposit amount. Please try again.");
            return;
        }
        bal += amt;
        System.out.println("The amount deposited into your account is " + amt);
        checkBalance();
    }

    public static void withdraw() {
        System.out.print("Enter the amount to withdraw: ");
        double amt = sc.nextDouble();
        if (amt <= 0) {
            System.out.println("Invalid withdrawal amount. Please try again.");
            return;
        }
        if (amt > bal) {
            System.out.println("Insufficient balance. Cannot withdraw.");
        } else {
            bal -= amt;
            System.out.println("The amount withdrawn from your account is " + amt);
        }
        checkBalance();
    }

    public static void exit() {
        System.out.println("Thank you. Have a nice day!");
    }
}
