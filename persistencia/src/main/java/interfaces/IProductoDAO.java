package interfaces;

import java.sql.SQLException;
import java.util.List;

import entidades.Producto;

public interface IProductoDAO {
    List<Producto> getProductos() throws SQLException;
}
