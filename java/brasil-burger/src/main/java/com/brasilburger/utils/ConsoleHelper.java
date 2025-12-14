package com.brasilburger.utils;
import java.util.Scanner;

public class ConsoleHelper {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readString(String message) {
        System.out.print(message + ": ");
        return scanner.nextLine().trim();
    }

    public static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message + ": ");
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }
    }

    public static double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message + ": ");
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre décimal valide.");
            }
        }
    }

    public static void printHeader(String title) {
        System.out.println("\n=== " + title + " ===\n");
    }

    public static void pause() {
        System.out.println("Appuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }
}
