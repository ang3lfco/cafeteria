package interfaces;

import java.sql.SQLException;
import java.util.List;

import entidades.Producto;

public interface IProductoNegocio {
    List<Producto> obtenerProductos() throws SQLException;
}
