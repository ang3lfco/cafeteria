package interfaces;

import java.sql.SQLException;
import java.util.List;

import entidades.OrdenProducto;

public interface IOrdenProductoDAO {
    void crear(OrdenProducto ordenProducto) throws SQLException;
    List<OrdenProducto> obtenerOrdenesProductos() throws SQLException;
}
