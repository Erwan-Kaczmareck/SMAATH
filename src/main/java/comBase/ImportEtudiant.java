package comBase;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ImportEtudiant {

    public static void importEtudiant(String nom, String prenom, String classe) {

        if (nom == null || prenom == null || classe == null || nom.trim().isEmpty() || prenom.trim().isEmpty() || classe.trim().isEmpty()) {
            System.out.println("Erreur : Données invalides !");
            return;
        }

        // Connexion à la base de données
        Connection conn = ConnectionBase.connect();
        if (conn == null) {
            System.out.println("Échec de connexion à la base !");
            return;
        }

        // Récupérer tous les étudiants existants
        Set<String> existingStudents = getExistingStudents(conn);

        // Clé unique pour l'étudiant
        String studentKey = nom + "|" + prenom + "|" + classe;

        // Vérifie si l'étudiant existe déjà dans la base
        if (existingStudents.contains(studentKey)) {
            System.out.println("L'étudiant existe déjà : " + nom + " " + prenom + " (" + classe + ")");
        } else {
            // Requête d'insertion
            String insertSql = "INSERT INTO Etudiant (nom, prenom, classe) VALUES (?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, nom);
                insertStmt.setString(2, prenom);
                insertStmt.setString(3, classe);
                insertStmt.executeUpdate();
                System.out.println("Étudiant ajouté : " + nom + " " + prenom + " (" + classe + ")");
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'insertion dans la base !");
                e.printStackTrace();
            }
        }

        ConnectionBase.closeConnection(conn);
    }

    private static Set<String> getExistingStudents(Connection conn) {
        Set<String> students = new HashSet<>();
        String sql = "SELECT nom, prenom, classe FROM Etudiant";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nom = rs.getString("nom").trim();
                String prenom = rs.getString("prenom").trim();
                String classe = rs.getString("classe").trim();
                students.add(nom + "|" + prenom + "|" + classe); // Clé unique pour chaque étudiant
            }

            System.out.println("Étudiants existants récupérés : " + students.size());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des étudiants !");
            e.printStackTrace();
        }

        return students;
    }
}
