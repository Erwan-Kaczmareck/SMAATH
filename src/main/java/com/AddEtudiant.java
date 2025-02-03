package com;

import comBase.ImportEtudiant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AddEtudiant {

    public static void ajouterEtudiant() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez le nom de l'étudiant : ");
        String nom = scanner.nextLine().trim();

        System.out.print("Entrez le prénom de l'étudiant : ");
        String prenom = scanner.nextLine().trim();

        System.out.print("Entrez la classe de l'étudiant : ");
        String classe = scanner.nextLine().trim();

        ImportEtudiant.importEtudiant(nom, prenom, classe);
    }
}
