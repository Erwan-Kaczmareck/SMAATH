package comBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBase {
    private static final String URL = "jdbc:sqlite:src/SmaathDB.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection(URL);
            System.out.println("Connexion réussie à SQLite !");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur : Driver SQLite introuvable !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données !");
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connexion fermée !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture de la connexion !");
            e.printStackTrace();
        }
    }
}
