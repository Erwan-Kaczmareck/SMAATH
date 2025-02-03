package comBase;
import comBase.ConnectionBase;
import com.ImportCSV;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ImportTable {

    public static void importData(String filePath) {
        List<String[]> data = ImportCSV.readCsvFile(filePath);
        if (data.isEmpty()) {
            System.out.println("Aucune donnée à importer !");
            return;
        }

        Connection conn = ConnectionBase.connect();
        if (conn == null) {
            System.out.println("Échec de connexion à la base !");
            return;
        }

        String sql = "INSERT INTO eleve (nom, prenom, classe) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (String[] ligne : data) {
                pstmt.setString(1, ligne[0]); // Nom
                pstmt.setString(2, ligne[1]); // Prénom
                pstmt.setString(3, ligne[2]); // Classe
                pstmt.executeUpdate();
            }

            System.out.println("Importation du CSV terminée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion dans la base !");
            e.printStackTrace();
        } finally {
            ConnectionBase.closeConnection(conn);
        }
    }
}
