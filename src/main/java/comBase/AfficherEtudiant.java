package comBase;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AfficherEtudiant {

    public static Set<String> ListeEtudiant() {

        Connection conn = ConnectionBase.connect();
        if (conn == null) {
            System.out.println("Échec de connexion à la base !");
            return null;
        }

        //Récupérer tous les étudiants existants
        Set<String> existingStudents = getExistingStudents(conn);
        return existingStudents;
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


    private static Set<String> getStudentsByClass(Connection conn, String classe) {
        Set<String> students = new HashSet<>();
        String sql = "SELECT nom, prenom FROM Etudiant WHERE classe = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, classe);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                students.add(nom + " " + prenom);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des étudiants de la classe !");
            e.printStackTrace();
        }

        return students;
    }

    public static String getStudentId(Connection conn, String nom, String prenom) {
        String sql = "SELECT id_etudiant FROM Etudiant WHERE nom = ? AND prenom = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("id_etudiant");
            } else {
                System.out.println("Étudiant non trouvé : " + nom + " " + prenom);
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'ID de l'étudiant !");
            e.printStackTrace();
        }

        return null;
    }
}
