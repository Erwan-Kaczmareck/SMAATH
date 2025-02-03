package com;

import comBase.AfficherEtudiant;
import comBase.ConnectionBase;
import comBase.AfficherClasse;
import comBase.ImportNote;

import java.sql.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class AddNote {

    public static void addNotesToClass() {
        // Connexion à la base de données
        Connection conn = ConnectionBase.connect();
        if (conn == null) {
            System.out.println("Échec de connexion à la base !");
            return;
        }

        // Afficher les classes disponibles
        Set<String> classes = AfficherClasse.ListeClasse();
        if (classes == null || classes.isEmpty()) {
            System.out.println("Aucune classe disponible.");
            return;
        }

        // Demander à l'utilisateur de choisir une classe
        Scanner sc = new Scanner(System.in);
        System.out.println("Classes disponibles : ");
        for (String classe : classes) {
            System.out.println(classe);
        }
        System.out.print("Entrez la classe pour laquelle vous voulez ajouter des notes : ");
        String classeChoisie = sc.nextLine().trim();

        // Vérifier si la classe choisie existe dans la base de données
        if (!classes.contains(classeChoisie)) {
            System.out.println("Classe non trouvée. Veuillez réessayer.");
            return;
        }

        // Demander à l'utilisateur le nom du cours
        System.out.print("Entrez le nom du cours : ");
        String cours = sc.nextLine().trim();

        // Ajouter les notes aux étudiants de cette classe
        Set<String> students = AfficherClasse.getStudentsByClass(conn, classeChoisie);
        if (students.isEmpty()) {
            System.out.println("Aucun étudiant trouvé pour la classe " + classeChoisie);
            return;
        }

        ImportNote.addNotesToStudents(students, cours);
    }


}