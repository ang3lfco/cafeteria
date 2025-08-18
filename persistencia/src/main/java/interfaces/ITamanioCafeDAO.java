package interfaces;

import java.sql.SQLException;


public interface ITamanioCafeDAO {
    double getPrecio(int idCafe, int idTamanio) throws SQLException;
}
