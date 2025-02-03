package com;

import java.util.Scanner;

public class Menu {
    public static void menuApp() {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== MENU PRINCIPAL ===");
        System.out.println("1. Importer un fichier CSV");
        System.out.println("2. Quitter");
        System.out.print("Choisissez une option : ");

        int choix = sc.nextInt();
        sc.nextLine();

        switch (choix) {
            case 1:
                System.out.println("Donnez le chemin vers le fichier CSV sous la forme de 'C:\\\\Users\\\\erwan\\\\Downloads\\\\Eleve.csv' :");
                String filePath = sc.nextLine().trim();
                System.out.println("Fichier sélectionné : " + filePath);
                ImportCSV.readCsvFile(filePath);
            case 2:
                System.out.println("Fermeture de l'application...");
                break;
            default:
                System.out.println("Option invalide. Veuillez réessayer.");
        }

        sc.close();
    }
}
