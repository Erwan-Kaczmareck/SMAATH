package com;

import java.util.Scanner;

public class Menu {
    public static void menuApp() {
        Scanner sc = new Scanner(System.in); // Initialisation du scanner
        int choix = -1;


        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Importer un fichier CSV");
            System.out.println("2. Ajouter un étudiant");
            System.out.println("3. Ajouter une note à une classe");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option : ");
            
            while (!sc.hasNextInt()) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                sc.next();
            }

            choix = sc.nextInt();
            sc.nextLine();


            switch (choix) {
                case 1:
                    System.out.println("Donnez le chemin vers le fichier CSV sous la forme de 'C:\\\\Users\\\\erwan\\\\Downloads\\\\Eleve.csv' :");
                    String filePath = sc.nextLine().trim();
                    System.out.println("Fichier sélectionné : " + filePath);
                    ImportCSV.readCsvFile(filePath);
                    break;

                case 2:
                    AddEtudiant.ajouterEtudiant();
                    break;

                case 3:
                    AddNote.addNotesToClass();
                    break;

                case 4:
                    System.out.println("Fermeture de l'application...");
                    break;

                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        } while (choix != 4); // Continue tant que l'utilisateur ne choisit pas de quitter

    }
}
