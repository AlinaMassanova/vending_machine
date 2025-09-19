package util;

import java.util.Scanner;

public class CoinAcceptor implements PaymentDevice {
    private int amount;


    public CoinAcceptor(int initialAmount) {
        this.amount = initialAmount;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void promptTopUp() {

        System.out.println("Insert coin (enter integer amount) or type '0' to cancel:");
        String line = new Scanner(System.in).nextLine();
        try {
            int x = Integer.parseInt(line.trim());
            if (x > 0) {
                amount += x;
                System.out.println("Accepted: " + x);
            } else {
                System.out.println("No coins added.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. No coins added.");
        }
    }
}
