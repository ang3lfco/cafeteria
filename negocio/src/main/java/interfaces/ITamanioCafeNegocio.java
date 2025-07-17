package interfaces;

import java.sql.SQLException;

public interface ITamanioCafeNegocio {
    double obtenerPrecioCafe(int idCafe, int idTamanio) throws SQLException;
}
