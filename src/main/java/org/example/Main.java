package org.example;

import javafx.util.Pair;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AuthorisationManager authorisationManager = new AuthorisationManager();
        WalletManager walletManager = new WalletManager();
        walletManager.readCSV();
        authorisationManager.readCSV();
        if (walletManager.isEmpty()){
            System.out.println("Вас приветствует Система управления личными финансами!");
            System.out.println();
            User user = createAccount(scanner, authorisationManager);
            Wallet wallet = walletCreation(scanner);
            walletManager.addWallet(user.getLogin(), wallet);
            wallet.printWallet();
            userCommandReader(scanner, user, wallet, authorisationManager, walletManager);
        } else {
            System.out.println("Вас приветствует Система управления личными финансами!");
            asw(scanner,authorisationManager, walletManager);
        }
    }
    public static Pair<User, Boolean> authorisation(Scanner scanner, AuthorisationManager authorisationManager){
        User user = new User();
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        if (authorisationManager.isLoginAvailable(login)) {
            System.out.println("Такого логина не существует...");
            System.out.println("Повторите ввод...");
            System.out.println();
            return authorisation(scanner, authorisationManager);
        }

        for (int i = 0; i < 5; i++) {
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();
            System.out.println();
            user = new User(login, password);

            if (authorisationManager.ispasswordcorrect(user)) {
                System.out.printf("Добро пожаловать, %s!", login);
                System.out.println();
                System.out.println();
                return new Pair<>(user, true);
            }
            System.out.println("Неверно введенный пароль...");
            System.out.println();
        }
        return new Pair<>(user, false);
    }

    public static User createAccount(Scanner scanner, AuthorisationManager authorisationManager) {
        System.out.println("Создание нового пользователя...");
        System.out.print("Введите логин: ");
        String login;
        login = scanner.nextLine();
        while (!authorisationManager.isLoginAvailable(login)) {
            System.out.println();
            System.out.println("Введенный вами логин занят.");
            System.out.print("Придумайте другой логин: ");
            login = scanner.nextLine();
        }
        String password1;
        while (true) {
            System.out.print("Введите пароль: ");
            password1 = scanner.nextLine();
            System.out.print("Введите пароль повторно: ");
            String password2 = scanner.nextLine();
            if (password1.equals(password2)) {
                break;
            } else {
                System.out.println();
                System.out.println("Введенные вами пароли не совпадают, повторите ввод...");
            }
        }
        User user = new User(login, password1);
        authorisationManager.adduser(user);
        System.out.println();
        return user;
    }

    public static Wallet walletCreation(Scanner scanner) throws InputMismatchException {
        Wallet wallet = new Wallet();
        wallet.addIncomesConsole(scanner);
        wallet.addExpensesConsole(scanner);
        wallet.addBudgetsConsole(scanner);
        return wallet;
    }

    public static void userCommandReader(Scanner scanner, User user, Wallet wallet, AuthorisationManager autorisationManager, WalletManager walletManager){
        while (true){
            System.out.println("1. Вывести суммарные доходы/расходы/бюджет");
            System.out.println("2. Вывести доходы/расходы/бюджет по категориям");
            System.out.println("3. Вывести доходы/расходы/бюджет по выбранным категориям");
            System.out.println("4. Добавить доходы/расходы/бюджеты по выбранным категориям");
            System.out.println("5. Выйти из сессии");
            System.out.println("6. Выйти из приложения");
            System.out.print("Выберите действие (введите номер): ");
            System.out.println();
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
                    System.out.println("1. Вывести Доходы по выбранным категориям");
                    System.out.println("2. Вывести Расходы по выбранным категориям");
                    System.out.println("3. Вывести Бюджеты по выбранным категориям");
                    System.out.print("Выберите действие (введите номер): ");
                    System.out.println();
                    ans = scanner.nextLine();
                    switch (ans){
                        case "1":
                            System.out.println("Введите категории через пробел:");
                            String cats = scanner.nextLine();
                            wallet.printIncomesByStates(cats);
                            break;
                        case "2":
                            System.out.println("Введите категории через пробел:");
                            cats = scanner.nextLine();
                            wallet.printExpenseByStates(cats);
                            break;
                        case "3":
                            System.out.println("Введите категории через пробел:");
                            cats = scanner.nextLine();
                            wallet.printBudgetByStates(cats);
                            break;
                        default:
                            System.out.println("Неверный формат ввода данных...");
                            System.out.println();
                            break;
                    }
                    break;
                case "4" :
                    System.out.println("1. Добавить Доходы");
                    System.out.println("2. Добавить Расходы");
                    System.out.println("3. Добавить Бюджеты");
                    System.out.print("Выберите действие (введите номер): ");
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
                case "5":
                    System.out.println("Выход из сессии...");
                    System.out.println();
                    asw(scanner, autorisationManager, walletManager);
                    return;
                case "6" :
                    System.out.println("Выход из приложения...");
                    System.exit(0);
                default:
                    System.out.println("Неверный формат ввода данных...");
                    System.out.println();
            }
        }
    }

    public static void asw(Scanner scanner, AuthorisationManager autorisationManager, WalletManager walletManager){
        System.out.print("У вас уже есть учетная запись в приложении? (д/н): ");
        String anw = scanner.nextLine();
        System.out.println();
        if (anw.equalsIgnoreCase("д")){
            Pair<User, Boolean> autorisationResult = authorisation(scanner, autorisationManager);
            User user = autorisationResult.getKey();
            if(!autorisationResult.getValue()){
                System.out.println("Пароль был введен неправильно 5 раз. Программа останавливается...");
            } else {
                Wallet wallet = walletManager.getWallet(autorisationResult.getKey().getLogin());
                wallet.printWallet();
                userCommandReader(scanner, user, wallet, autorisationManager, walletManager);
            }
        } else if(anw.equalsIgnoreCase("н")){
            User user = createAccount(scanner, autorisationManager);
            Wallet wallet;
            try {
                wallet = walletCreation(scanner);
            } catch (InputMismatchException e) {
                wallet = walletCreation(scanner);
            }
            walletManager.addWallet(user.getLogin(), wallet);
            System.out.println();
            wallet.printWallet();
            userCommandReader(scanner, user, wallet, autorisationManager, walletManager);
        } else {
            System.out.println();
            System.out.println("Неверный формат ввода данных...");
            System.out.println();
            asw(scanner, autorisationManager, walletManager);
        }
    }
}