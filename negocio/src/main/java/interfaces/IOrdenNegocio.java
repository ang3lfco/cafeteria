package interfaces;

import java.sql.SQLException;
import java.util.List;

import entidades.Orden;

public interface IOrdenNegocio {
    int registrarOrden(Orden orden) throws SQLException;
    List<Orden> obtenerOrdenes() throws SQLException;
}
