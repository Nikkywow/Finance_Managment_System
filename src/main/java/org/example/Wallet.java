package org.example;

import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Wallet {
    private ArrayList<Pair<String, Float>> incomes = new ArrayList<>();
    private ArrayList<Pair<String, Float>> expenses = new ArrayList<>();
    private ArrayList<Pair<String, Float>> budgets = new ArrayList<>();
    private final String filename = "wallets.csv";

    public Wallet(){
    }

    public void addIncomes(String state, Float value){
        Pair<String,Float> income = new Pair<>(state, value);
        incomes.add(income);
    }

    public void addExpenses(String state, Float value){
        Pair<String,Float> expense = new Pair<>(state, value);
        expenses.add(expense);
    }

    public void addBudgets(String state, Float value){
        Pair<String,Float> budget = new Pair<>(state, value);
        budgets.add(budget);
    }

    //Подсчет общей прибыли/расходов/бюджета
    public Float getOverallIncome(){
        Float overallincome = 0F;
        for(Pair<String, Float> income : incomes){
            overallincome += income.getValue();
        }
        return overallincome;
    }

    public Float getOverallExpense(){
        Float overallexpense = 0F;
        for(Pair<String, Float> expense : expenses){
            overallexpense += expense.getValue();
        }
        return overallexpense;
    }

    public Float getOverallBudget(){
        Float overallbudget = 0F;
        for(Pair<String, Float> budget : budgets){
            overallbudget += budget.getValue();
        }
        return overallbudget;
    }

    public HashMap<String, Float> getIncomePerState(){
        HashMap<String, Float> incomePerState = new HashMap<>();
        for(Pair<String, Float> income : incomes){
            if(!incomePerState.containsKey(income.getKey())){
                incomePerState.put(income.getKey(), income.getValue());
            } else {
                incomePerState.replace(income.getKey(), incomePerState.get(income.getKey()) + income.getValue());
            }
        }
        return incomePerState;
    }

    public HashMap<String, Float> getExpensePerState() {
        HashMap<String, Float> expensePerState = new HashMap<>();
        for (Pair<String, Float> expense : expenses) {
            if (!expensePerState.containsKey(expense.getKey())) {
                expensePerState.put(expense.getKey(), expense.getValue());
            } else {
                expensePerState.replace(expense.getKey(), expensePerState.get(expense.getKey()) + expense.getValue());
            }
        }
        return expensePerState;
    }

    public HashMap<String, Float> getBudgetPerState() {
        HashMap<String, Float> budgetPerState = new HashMap<>();
        for (Pair<String, Float> budget : budgets) {
            if (!budgetPerState.containsKey(budget.getKey())) {
                budgetPerState.put(budget.getKey(), budget.getValue());
            } else {
                budgetPerState.replace(budget.getKey(), budgetPerState.get(budget.getKey()) + budget.getValue());
            }
        }
        return budgetPerState;
    }

    public HashMap<String, Float> getRemainingBudgetPerState() {
        HashMap<String, Float> budgetPerState = getBudgetPerState();
        HashMap<String, Float> remainingBudgetPerState = getBudgetPerState();
        for (Pair<String, Float> expense : expenses) {
            if (budgetPerState.keySet().contains(expense.getKey())){
                remainingBudgetPerState.put(expense.getKey(), remainingBudgetPerState.get(expense.getKey()) - expense.getValue());
            } else {
                remainingBudgetPerState.put(expense.getKey(), budgetPerState.get(expense.getKey()));
            }
        }
        return remainingBudgetPerState;
    }

    public void addIncomesConsole(Scanner scanner, String login){
        System.out.print("Введите количество новых статей доходов: ");
        int iter = scanner.nextInt();
        System.out.println("Далее вводите статью - значение через пробел: ");
        scanner.nextLine();
        for(int i = 0; i < iter; i++) {
            String[] input;
            input = scanner.nextLine().split(" ");
            this.incomes.add(new Pair<>(input[0], Float.valueOf(input[1])));
            addDataToCSV(login, "i", input[0], input[1]);
        }
    }

    public void addIncomesConsole(Scanner scanner){
        System.out.print("Введите количество новых статей доходов: ");
        int iter = scanner.nextInt();
        System.out.println("Далее вводите статью - значение через пробел: ");
        scanner.nextLine();
        for(int i = 0; i < iter; i++) {
            String[] input;
            input = scanner.nextLine().split(" ");
            this.incomes.add(new Pair<>(input[0], Float.valueOf(input[1])));
        }
    }

    public void addExpensesConsole(Scanner scanner, String login){
        System.out.print("Введите количество новых статей расходов: ");
        int iter = scanner.nextInt();
        System.out.println("Далее вводите статью - значение через пробел: ");
        scanner.nextLine();
        for(int i = 0; i < iter; i++) {
            String[] input;
            input = scanner.nextLine().split(" ");
            this.expenses.add(new Pair<>(input[0], Float.valueOf(input[1])));
            addDataToCSV(login, "e", input[0], input[1]);
        }
    }

    public void addExpensesConsole(Scanner scanner){
        System.out.print("Введите количество новых статей расходов: ");
        int iter = scanner.nextInt();
        System.out.println("Далее вводите статью - значение через пробел: ");
        scanner.nextLine();
        for(int i = 0; i < iter; i++) {
            String[] input;
            input = scanner.nextLine().split(" ");
            this.expenses.add(new Pair<>(input[0], Float.valueOf(input[1])));
        }
    }

    public void addBudgetsConsole(Scanner scanner, String login){
        System.out.print("Введите количество новых бюджетов по расходам: ");
        int iter = scanner.nextInt();
        System.out.println("Далее вводите статью - значение через пробел: ");
        scanner.nextLine();
        for(int i = 0; i < iter; i++) {
            String[] input;
            input = scanner.nextLine().split(" ");
            this.budgets.add(new Pair<>(input[0], Float.valueOf(input[1])));
            addDataToCSV(login, "b", input[0], input[1]);
        }
    }

    public void addBudgetsConsole(Scanner scanner){
        System.out.print("Введите количество новых бюджетов по расходам: ");
        int iter = scanner.nextInt();
        System.out.println("Далее вводите статью - значение через пробел: ");
        scanner.nextLine();
        for(int i = 0; i < iter; i++) {
            String[] input;
            input = scanner.nextLine().split(" ");
            this.budgets.add(new Pair<>(input[0], Float.valueOf(input[1])));
        }
    }



    public void printWallet() {
        System.out.println("Общий доход: " + getOverallIncome());
        System.out.println();
        System.out.println("Доход по категориям:");
        for (Map.Entry<String, Float> entry : getIncomePerState().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println();
        System.out.println("Общий расход: " + getOverallExpense());
        System.out.println();
        System.out.println("Расходы по категориям:");
        for (Map.Entry<String, Float> entry : getExpensePerState().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println();
        System.out.println("Общий бюджет: " + getOverallBudget());
        System.out.println();
        System.out.println("Бюджет по категориям:");
        for (Map.Entry<String, Float> entry : getBudgetPerState().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println();
        System.out.println("Оставшийся бюджет по категориям:");
        for (Map.Entry<String, Float> entry : getRemainingBudgetPerState().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println();
    }

    public ArrayList<Pair<String, Float>> getIncomes(){
        return this.incomes;
    }

    public ArrayList<Pair<String, Float>> getExpenses(){
        return this.expenses;
    }

    public ArrayList<Pair<String, Float>> getBudgets(){
        return this.budgets;
    }

    public void addDataToCSV(String login, String category, String state, String sum){
        try {
            List<String> row = Arrays.asList(login, category, state, sum);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.filename, true));
            bufferedWriter.write(String.join(",", row));
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Ошибка: Данные не были записаны");
        }
    }
}
