package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class AutorisationManager {
    private HashMap<String, String> userdata = new HashMap<>();
    private final String filename = "user_data.csv";

    public AutorisationManager(){
    }
    public void adduser(User user){
        userdata.put(user.getLogin(), user.getPassword());
        writeCSV(user);
    }

    public boolean ispasswordcorrect(User user){
        try {


            if (userdata.get(user.getLogin()).equals(user.getPassword())) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e){
            return false;
        }
    }
    public void readCSV(){
        try (Scanner scanner = new Scanner(new File(this.filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<String> row = Arrays.asList(line.split(","));
                userdata.put(row.get(0), row.get(1));
            }
        } catch (IOException e) {
            createCSV();
        }
    }

    private void createCSV() {
        try {
            CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(this.filename), CSVFormat.DEFAULT);
            csvPrinter.print("");
        } catch (IOException e) {
            System.out.println("не выполнилось");
        }
    }

    private void writeCSV(User user) {
        try {
            List<String> row = Arrays.asList(user.getLogin(), user.getPassword());
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.filename, true));
            bufferedWriter.write(String.join(",", row));
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e){
            System.out.println("Ошибка: Данные не были записаны");
        }
    }

    public boolean isLoginAvailable(String login){
        if(userdata.keySet().contains(login)){
            return false;
        }
        else {
            return true;
        }
    }
}
