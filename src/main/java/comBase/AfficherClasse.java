package comBase;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AfficherClasse {

    public static Set<String> ListeClasse() {

        Connection conn = ConnectionBase.connect();
        if (conn == null) {
            System.out.println("Échec de connexion à la base !");
            return null;
        }

        //Récupérer toutes les classes
        Set<String> existingClass = getExistingClass(conn);
        return existingClass;
    }

    private static Set<String> getExistingClass(Connection conn) {
        Set<String> listClass = new HashSet<>();
        String sql = "SELECT DISTINCT classe FROM Etudiant";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                listClass.add(rs.getString("classe"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des étudiants !");
            e.printStackTrace();
        }

        return listClass;
    }

    public static Set<String> getStudentsByClass(Connection conn, String classe) {
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
}
