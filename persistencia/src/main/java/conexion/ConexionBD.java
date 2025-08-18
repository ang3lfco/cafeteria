package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private final String usuario = "root";
    private final String contraseña = "mysql123";
    private final String servidor = "127.0.0.1";
    private final String base = "cafeteria";
    private final String url = "jdbc:mysql://" + servidor + "/" + base;

    public Connection crearConexion() throws SQLException {
        return DriverManager.getConnection(url, usuario, contraseña);
    }
}
