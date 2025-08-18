package interfaces;

import java.sql.SQLException;
import java.util.List;

import entidades.Postre;

public interface IPostreNegocio {
    List<Postre> obtenerPostres() throws SQLException;
    double obtenerPrecio(int id) throws SQLException;
}
