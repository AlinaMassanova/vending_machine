package util;

import java.util.Scanner;

public class CardAcceptor implements PaymentDevice {
    private int amount;

    public CardAcceptor(int initialAmount) {
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
        Scanner sc = new Scanner(System.in);
        System.out.println("Card payment selected. Enter OTP '1234' to simulate +100, or anything else to cancel:");
        System.out.print("Card number (simulate): ");
        sc.nextLine();
        System.out.print("OTP: ");
        String otp = sc.nextLine();
        if ("1234".equals(otp.trim())) {
            amount += 100;
            System.out.println("Payment accepted. Added 100.");
        } else {
            System.out.println("Payment declined.");
        }
    }
}
