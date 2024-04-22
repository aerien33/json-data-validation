package com.domain.ui;

import com.domain.service.JsonService;
import com.domain.service.impl.JsonServiceImpl;

import java.util.Scanner;

public class ValidatorUI {

    JsonService service = new JsonServiceImpl();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter name of the file to validate, 'x' to exit:");
            String fileName = scanner.nextLine().trim();

            if (fileName.equals("x")) {
                break;
            }

            System.out.println(service.getResult(fileName));
        }

    }
}