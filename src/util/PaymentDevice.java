package util;

public interface PaymentDevice {
    int getAmount();
    void setAmount(int amount);

    default void promptTopUp() {
    }
}
