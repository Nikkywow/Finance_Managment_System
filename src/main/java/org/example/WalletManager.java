package org.example;

import javafx.util.Pair;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WalletManager {
    HashMap<String, Wallet> wallets = new HashMap<>();
    private String filepath = "data.csv";

    public WalletManager(){}

    public void addWallet(String login, Wallet wallet){
        this.wallets.put(login, wallet);
    }

    public Wallet getWallet(String login){
        return this.wallets.get(login);
    }

    public void createCSV() {
        try {
            CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(this.filepath), CSVFormat.DEFAULT);
            csvPrinter.print("");
        } catch (IOException e) {
            System.out.println("не выполнилось");
        }
    }

    private void readCSV(){

    }
    public void writeCSV(String login){
        Wallet wallet = getWallet(login);
        ArrayList<Pair<String, Float>> incomes = wallet.getIncomes();
        ArrayList<Pair<String, Float>> expenses = wallet.getExpenses();
        ArrayList<Pair<String, Float>> budgets = wallet.getBudgets();
        for (Pair<String, Float> income : incomes) {
            try {
                List<String> row = Arrays.asList(login, income.getKey(), income.getValue().toString());
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.filepath, true));
                bufferedWriter.write(String.join(",", row));
                bufferedWriter.newLine();
                bufferedWriter.close();
            } catch (IOException e) {
                System.out.println("Ошибка: Данные не были записаны");
            }
        }

        for (Pair<String, Float> expense : expenses) {
            try {
                List<String> row = Arrays.asList(login, expense.getKey(), expense.getValue().toString());
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.filepath, true));
                bufferedWriter.write(String.join(",", row));
                bufferedWriter.newLine();
                bufferedWriter.close();
            } catch (IOException e) {
                System.out.println("Ошибка: Данные не были записаны");
            }
        }

        for (Pair<String, Float> budget : budgets) {
            try {
                List<String> row = Arrays.asList(login, budget.getKey(), budget.getValue().toString());
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.filepath, true));
                bufferedWriter.write(String.join(",", row));
                bufferedWriter.newLine();
                bufferedWriter.close();
            } catch (IOException e) {
                System.out.println("Ошибка: Данные не были записаны");
            }
        }
    }


}


