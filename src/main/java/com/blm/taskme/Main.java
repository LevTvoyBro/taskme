package com.blm.taskme;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

//@SpringBootApplication
public class Main{
    public static void main(String[] args) throws IOException {
        //SpringApplication.run(Main.class, args);
        CSVReader reader = new CSVReader(new FileReader("src/main/resources/service_a_import.csv"), ';' , '\'', '/');
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            System.out.println(Arrays.toString(nextLine));
        }
    }
}