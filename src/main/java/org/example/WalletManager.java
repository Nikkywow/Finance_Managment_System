package org.example;

import javafx.util.Pair;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class WalletManager {
    HashMap<String, Wallet> wallets = new HashMap<>();
    private String filepath = "wallets.csv";

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
        try (Scanner scanner = new Scanner(new File(this.filepath))) {
            List<List<String>> data = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<String> row = Arrays.asList(line.split(","));
                data.add(row);
            }
            Wallet wallet = new Wallet();
            String currLogin = data.get(0).get(0);
            String login = currLogin;
            int i = 0;
            while (currLogin.equals(login)){
                login = data.get(i).get(0);
                if (data.get(i).get(1).equals("i")){
                    wallet.addIncomes(data.get(i).get(2), Float.parseFloat(data.get(i).get(3)));
                } else if(data.get(i).get(1).equals("e")){
                    wallet.addExpenses(data.get(i).get(2), Float.parseFloat(data.get(i).get(3)));
                } else if (data.get(i).get(1).equals("b")) {
                    wallet.addBudgets(data.get(i).get(2), Float.parseFloat(data.get(i).get(3)));
                }
            }

        } catch (IOException e) {
            createCSV();
        }
    }
    public void writeCSV(String login){
        Wallet wallet = getWallet(login);
        ArrayList<Pair<String, Float>> incomes = wallet.getIncomes();
        ArrayList<Pair<String, Float>> expenses = wallet.getExpenses();
        ArrayList<Pair<String, Float>> budgets = wallet.getBudgets();
        for (Pair<String, Float> income : incomes) {
            try {
                List<String> row = Arrays.asList(login, "i", income.getKey(), income.getValue().toString());
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
                List<String> row = Arrays.asList(login, "e", expense.getKey(), expense.getValue().toString());
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
                List<String> row = Arrays.asList(login, "b", budget.getKey(), budget.getValue().toString());
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


