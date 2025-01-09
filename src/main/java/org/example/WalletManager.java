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
        writeCSV(login);
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

    public void readCSV() {
        try (Scanner scanner = new Scanner(new File(this.filepath))) {
            HashMap<String, Wallet> tempWallets = new HashMap<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] columns = line.split(",");

                if (columns.length < 4) {
                    System.out.println("Ошибка в формате строки: " + line);
                    continue; // Пропускаем некорректную строку
                }

                String login = columns[0];  // Логин пользователя
                String type = columns[1];  // Тип записи (i, e, b)
                String category = columns[2]; // Категория
                Float value;

                try {
                    value = Float.parseFloat(columns[3]); // Значение
                } catch (NumberFormatException e) {
                    System.out.println("Некорректное значение: " + columns[3]);
                    continue; // Пропускаем строку с ошибкой
                }

                // Получаем или создаём Wallet для текущего пользователя
                Wallet wallet = tempWallets.getOrDefault(login, new Wallet());

                // Заполняем Wallet в зависимости от типа записи
                switch (type) {
                    case "i":
                        wallet.addIncomes(category, value);
                        break;
                    case "e":
                        wallet.addExpenses(category, value);
                        break;
                    case "b":
                        wallet.addBudgets(category, value);
                        break;
                    default:
                        System.out.println("Неизвестный тип записи: " + type);
                }

                // Обновляем HashMap
                tempWallets.put(login, wallet);
            }

            // Обновляем wallets из временного хранилища
            this.wallets = tempWallets;

            System.out.println("Данные успешно загружены из файла " + this.filepath);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + this.filepath);
            createCSV(); // Создаем файл, если его нет
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
    public boolean isEmpty(){
        return wallets.isEmpty();
    }

}


