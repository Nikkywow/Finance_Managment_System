package org.example;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        test();
        Scanner scanner = new Scanner(System.in);
        AutorisationManager autorisationManager = new AutorisationManager();
        WalletManager walletManager = new WalletManager();
        while(true){
            System.out.println("Вас приветствует Система управления личными финансами!");
            System.out.print("У вас уже есть учетная запись в приложении? (д/н): ");
            String anw = scanner.nextLine();
            if (anw.equalsIgnoreCase("д")){
                Pair<User, Boolean> autorisationResult = autorisation(scanner, autorisationManager);
                if(!autorisationResult.getValue()){
                    System.out.println("Пароль был введен неправильно 5 раз. Программа останавливается...");
                    return;
                } else {
                    Wallet wallet = walletManager.getWallet(autorisationResult.getKey().getLogin());
                    wallet.printWallet();
                    //Должны выводиться доходы, расходы и бюджеты. Должно быть предложено добавить новые статьи, после добавления все пересчитывается.
                }
            } else if(anw.equalsIgnoreCase("н")){
                User user = createAccount(scanner, autorisationManager);
                Wallet wallet = walletCreation(scanner);
                walletManager.addWallet(user.getLogin(), wallet);
                wallet.printWallet();
            }
        }
    }
    public static Pair<User, Boolean> autorisation(Scanner scanner, AutorisationManager autorisationManager){
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        User user = new User(login, password);
        if (autorisationManager.ispasswordcorrect(user)){
            return new Pair<>(user, true);
        }
        else {
            return new Pair<>(user, false);
        }
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
        return user;
    }

    public static Wallet walletCreation(Scanner scanner){
        Wallet wallet = new Wallet();
        wallet.addIncomesConsole(scanner);
        wallet.addExpensesConsole(scanner);
        wallet.addBudgetsConsole(scanner);
        return wallet;
    }


    public static void test(){
        Scanner scanner = new Scanner(System.in);
        WalletManager walletManager = new WalletManager();
        walletManager.createCSV();
        Wallet wallet = new Wallet();
        wallet.addIncomes("work", 100000F);
        wallet.addIncomes("work", 29000F);
        wallet.addIncomes("bonus", 23000F);
        wallet.addIncomes("work", 299000F);
        wallet.addExpenses("adu", 5000F);
        wallet.addExpenses("adu", 15000F);
        wallet.addExpenses("adu", 10000F);
        wallet.addExpenses("adu", 20000F);
        wallet.addExpenses("kids", 1000F);
        wallet.addExpenses("food", 2444F);
        wallet.addExpenses("food", 10000F);
        wallet.addBudgets("food", 150000F);
        wallet.addBudgets("adu", 300000F);
        wallet.addBudgets("kids", 25000F);

        wallet.addIncomesConsole(scanner);
        wallet.printWallet();

        walletManager.addWallet("nikita", wallet);
        walletManager.writeCSV("nikita");
    }

}