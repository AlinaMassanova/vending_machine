import util.CardAcceptor;
import util.CoinAcceptor;
import util.PaymentDevice;
import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();
    private final PaymentDevice paymentDevice;
    private final Scanner scanner = new Scanner(System.in);

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });


        System.out.println("Выберите способ оплаты: 1 - Монеты, 2 - Карта");
        String choice = scanner.nextLine();

        if ("1".equals(choice)) {
            paymentDevice = new CoinAcceptor(100); // начальный баланс 100 монет
            System.out.println("Вы выбрали оплату монетами.");
        } else {
            paymentDevice = new CardAcceptor(500); // баланс карты 500
            System.out.println("Вы выбрали оплату картой.");
        }
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        print("Ваш баланс: " + paymentDevice.getAmount());
        print(" a - Пополнить баланс");

        UniversalArray<Product> allowProducts = getAllowedProducts();
        chooseAction(allowProducts);
    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (paymentDevice.getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        showActions(products);
        print(" h - Выйти");
        String line = fromConsole().trim();

        if (line.isEmpty()) {
            print("Пустой ввод. Попробуйте ещё раз.");
            return;
        }

        String action = line.substring(0, 1);


        if ("a".equalsIgnoreCase(action)) {
            paymentDevice.promptTopUp();
            return;
        }
        if ("h".equalsIgnoreCase(action)) {
            isExit = true;
            return;
        }


        boolean found = false;
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            String letterName = p.getActionLetter().name();
            String letterValue = String.valueOf(p.getActionLetter().getValue());
            if (letterName.equalsIgnoreCase(action) || letterValue.equalsIgnoreCase(action)) {
                found = true;
                if (paymentDevice.getAmount() >= p.getPrice()) {
                    paymentDevice.setAmount(paymentDevice.getAmount() - p.getPrice());
                    print("Вы купили " + p.getName());
                } else {
                    print("Недостаточно средств для покупки " + p.getName());
                }
                break;
            }
        }

        if (!found) {
            print("Недопустимая буква. Попробуйте ещё раз.");
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        String line = scanner.nextLine();
        return line == null ? "" : line;
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
