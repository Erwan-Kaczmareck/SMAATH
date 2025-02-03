package comBase;

import comBase.AfficherEtudiant;
import comBase.ConnectionBase;
import comBase.AfficherClasse;

import java.sql.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ImportNote {

    public static void addNotesToStudents(Set<String> students, String cours) {

        Connection conn = ConnectionBase.connect();
        // Requête d'insertion
        String insertSql = "INSERT INTO Notes (cours, notes, id_etudiant_notes) VALUES (?, ?, ?)";

        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql);
             Scanner sc = new Scanner(System.in)) {

            // Parcourir tous les étudiants et ajouter leurs notes
            for (String student : students) {

                String[] nameParts = student.split(" ");
                if (nameParts.length != 2) {
                    System.out.println("Nom ou prénom incorrect pour : " + student);
                    continue;
                }

                String nom = nameParts[0];
                String prenom = nameParts[1];

                System.out.println("Entrez la note pour l'étudiant " + student + " dans le cours " + cours + ":");
                double note = sc.nextDouble();

                // Récupérer l'id de l'étudiant à partir de son nom et prénom
                String studentId = AfficherEtudiant.getStudentId(conn, nom, prenom);
                if (studentId != null) {
                    insertStmt.setString(1, cours);
                    insertStmt.setDouble(2, note);
                    insertStmt.setString(3, studentId);
                    insertStmt.executeUpdate();
                    System.out.println("Note ajoutée pour l'étudiant " + student);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout des notes !");
            e.printStackTrace();
        }

        ConnectionBase.closeConnection(conn);
    }
}
