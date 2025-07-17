package interfaces;

import java.sql.SQLException;
import java.util.List;

import entidades.OrdenProducto;

public interface IOrdenProductoNegocio {
    void registrarOrdenProducto(OrdenProducto ordenProducto) throws SQLException;
    List<OrdenProducto> obtenerOrdenesProductos() throws SQLException;
}