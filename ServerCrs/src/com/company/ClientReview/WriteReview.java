package com.company.ClientReview;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class WriteReview {
    public int writeReviewInFile(String cmpName, String login, String text) {
        int result =0;
        try (
                FileWriter writer = new FileWriter("review.txt", true)) {
            writer.write("Логин клиента: "+ login + "\n");
            writer.write("Название компании: "+ cmpName + "\n");
            writer.write(text);
            writer.append("\n=============================");
            writer.append("\n\n");
            writer.flush();
            result = 1;
        } catch (
                IOException ex) {

            System.out.println(ex.getMessage());
        }
        return result;
    }


    public ArrayList<String> selectReview(){
        ArrayList<String> review = new ArrayList<String>();
        try {

            FileReader fr= new FileReader("review.txt");
            Scanner scan = new Scanner(fr);

            while (scan.hasNextLine()) {
                review.add(scan.nextLine());
            }

            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return review;
    }

    public ArrayList<String> selectLogs(){
        ArrayList<String> logs = new ArrayList<String>();
        try {

            FileReader fr= new FileReader("logs.txt");
            Scanner scan = new Scanner(fr);

            while (scan.hasNextLine()) {
                logs.add(scan.nextLine());
            }

            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logs;
    }

    public int clearFile() {
        int result =0;
        try (
                FileWriter writer = new FileWriter("review.txt", false)) {
            writer.write("");
            writer.flush();
            result = 1;
        } catch (
                IOException ex) {

            System.out.println(ex.getMessage());
        }
        return result;
    }

    public int clearLogs() {
        int result =0;
        try (
                FileWriter writer = new FileWriter("logs.txt", false)) {
            writer.write("");
            writer.flush();
            result = 1;
        } catch (
                IOException ex) {

            System.out.println(ex.getMessage());
        }
        return result;
    }
}
