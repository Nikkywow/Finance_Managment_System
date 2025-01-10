package org.example;

import javafx.util.Pair;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AutorisationManager autorisationManager = new AutorisationManager();
        WalletManager walletManager = new WalletManager();
        walletManager.readCSV();
        autorisationManager.readCSV();
        if (walletManager.isEmpty()){
            System.out.println("Вас приветствует Система управления личными финансами!");
            User user = createAccount(scanner, autorisationManager);
            Wallet wallet = walletCreation(scanner);
            walletManager.addWallet(user.getLogin(), wallet);
            wallet.printWallet();
            userComandReader(scanner, user, wallet);
        } else {
            System.out.println("Вас приветствует Система управления личными финансами!");
            asw(scanner,autorisationManager, walletManager);
        }
    }
    public static Pair<User, Boolean> autorisation(Scanner scanner, AutorisationManager autorisationManager){
        User user = new User();
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        for (int i = 0; i < 5; i++) {
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();
            System.out.println();
            user = new User(login, password);

            if (autorisationManager.ispasswordcorrect(user)) {
                return new Pair<>(user, true);
            }
            System.out.println();
            System.out.println("Неверно введенный пароль...");
            System.out.println();
        }
        return new Pair<>(user, false);
    }

    public static User createAccount(Scanner scanner, AutorisationManager autorisationManager) {
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        String password1;
        while (true) {
            System.out.print("Введите пароль: ");
            password1 = scanner.nextLine();
            System.out.print("Введите пароль повторно: ");
            String password2 = scanner.nextLine();
            if (password1.equals(password2)) {
                break;
            } else {
                System.out.println("Введенные вами пароли не совпадают, повторите ввод...");
            }
        }
        User user = new User(login, password1);
        autorisationManager.adduser(user);
        System.out.println();
        return user;
    }

    public static Wallet walletCreation(Scanner scanner){
        Wallet wallet = new Wallet();
        wallet.addIncomesConsole(scanner);
        wallet.addExpensesConsole(scanner);
        wallet.addBudgetsConsole(scanner);
        return wallet;
    }

    public static void userComandReader(Scanner scanner, User user, Wallet wallet){
        while (true){
            System.out.println("1. Вывести суммарные доходы/расходы/бюджет");
            System.out.println("2. Вывести доходы/расходы/бюджет по категориям");
            System.out.println("3. Вывести доходы/расходы/бюджет по выбранным категориям");
            System.out.println("4. Добавить доходы/расходы/бюджеты по выбранным категориям");
            System.out.println("5. Выйти из приложения");
            String ans = scanner.nextLine();
            switch (ans){
                case "1" :
                    System.out.println("Общий доход: " + wallet.getOverallIncome());
                    System.out.println("Общие расходы: " + wallet.getOverallExpense());
                    System.out.println("Бюджет: " + wallet.getOverallBudget());
                    System.out.println();
                    break;
                case "2" :
                    wallet.printWallet();
                    break;
                case "3" :
                    break;
                case "4" :
                    System.out.println("1. Добавить доходы");
                    System.out.println("2. Добавить Расходы");
                    System.out.println("3. Добавить бюджеты");
                    System.out.println();
                    ans = scanner.nextLine();
                    switch (ans){
                        case "1":
                            wallet.addIncomesConsole(scanner, user.getLogin());
                            break;
                        case "2":
                            wallet.addExpensesConsole(scanner, user.getLogin());
                            break;
                        case "3":
                            wallet.addBudgetsConsole(scanner, user.getLogin());
                            break;
                        default:
                            System.out.println("Неверный формат ввода данных...");
                            System.out.println();
                            break;
                    }
                    break;
                case "5" :
                    return;
                default:
                    System.out.println("Неверный формат ввода данных...");
                    System.out.println();
            }
        }
    }

    public static void asw(Scanner scanner, AutorisationManager autorisationManager, WalletManager walletManager){
        System.out.print("У вас уже есть учетная запись в приложении? (д/н): ");
        String anw = scanner.nextLine();
        if (anw.equalsIgnoreCase("д")){
            Pair<User, Boolean> autorisationResult = autorisation(scanner, autorisationManager);
            User user = autorisationResult.getKey();
            if(!autorisationResult.getValue()){
                System.out.println("Пароль был введен неправильно 5 раз. Программа останавливается...");
                return;
            } else {
                Wallet wallet = walletManager.getWallet(autorisationResult.getKey().getLogin());
                wallet.printWallet();
                userComandReader(scanner, user, wallet);
            }
        } else if(anw.equalsIgnoreCase("н")){
            User user = createAccount(scanner, autorisationManager);
            Wallet wallet = walletCreation(scanner);
            walletManager.addWallet(user.getLogin(), wallet);
            wallet.printWallet();
            userComandReader(scanner, user, wallet);
        } else {
            System.out.println();
            System.out.println("Неверный формат ввода данных...");
            System.out.println();
            asw(scanner, autorisationManager, walletManager);
        }
    }
}