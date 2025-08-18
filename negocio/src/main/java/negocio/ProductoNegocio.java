package negocio;

import java.sql.SQLException;
import java.util.List;

import entidades.Producto;
import interfaces.IProductoDAO;
import interfaces.IProductoNegocio;

public class ProductoNegocio implements IProductoNegocio{
    private final IProductoDAO productoDAO;

    public ProductoNegocio(IProductoDAO productoDAO){
        this.productoDAO = productoDAO;
    }

    @Override
    public List<Producto> obtenerProductos() throws SQLException{
        return productoDAO.getProductos();
    }
}
