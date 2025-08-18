package interfaces;

import java.sql.SQLException;
import java.util.List;

import entidades.Orden;

public interface IOrdenDAO {
    int crear(Orden orden) throws SQLException;
    List<Orden> obtenerOrdenes() throws SQLException;
}
