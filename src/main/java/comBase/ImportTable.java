package comBase;

import comBase.ConnectionBase;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImportTable {

    public static void importUtilisateur(List<String[]> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("Aucune donnée à importer !");
            return;
        }

        Connection conn = ConnectionBase.connect();
        if (conn == null) {
            System.out.println("Échec de connexion à la base !");
            return;
        }

        //Récupérer tous les étudiants existants
        Set<String> existingStudents = getExistingStudents(conn);

        //Requête d'insertion
        String insertSql = "INSERT INTO Etudiant (nom, prenom, classe) VALUES (?, ?, ?)";

        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            for (String[] ligne : data) {
                String nom = ligne[0].trim();
                String prenom = ligne[1].trim();
                String classe = ligne[2].trim();

                String studentKey = nom + "|" + prenom + "|" + classe; // Clé unique

                // Vérifie si l'élève existe déjà dans la base
                if (existingStudents.contains(studentKey)) {
                    System.out.println("Elève déjà existant : " + nom + " " + prenom + " (" + classe + ")");
                    continue;
                }

                // Insère l'élève car il n'existe pas encore
                insertStmt.setString(1, nom);
                insertStmt.setString(2, prenom);
                insertStmt.setString(3, classe);
                insertStmt.executeUpdate();

                // Ajoute dans le Set pour éviter les doublons CSV
                existingStudents.add(studentKey);

                System.out.println("Elève ajouté : " + nom + " " + prenom + " (" + classe + ")");
            }

            System.out.println("Importation terminée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion dans la base !");
            e.printStackTrace();
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

                students.add(nom + "|" + prenom + "|" + classe); // Clé unique
            }

            System.out.println("Etudiants chargés depuis la base : " + students.size());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des étudiants !");
            e.printStackTrace();
        }

        return students;
    }
}
