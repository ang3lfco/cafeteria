package negocio;

import java.sql.SQLException;
import java.util.List;

import entidades.OrdenProducto;
import interfaces.IOrdenProductoDAO;
import interfaces.IOrdenProductoNegocio;

public class OrdenProductoNegocio implements IOrdenProductoNegocio{
    private IOrdenProductoDAO ordenProductoDAO;

    public OrdenProductoNegocio(IOrdenProductoDAO ordenProductoDAO){
        this.ordenProductoDAO = ordenProductoDAO;
    }

    @Override
    public void registrarOrdenProducto(OrdenProducto ordenProducto) throws SQLException{
        ordenProductoDAO.crear(ordenProducto);
    }

    @Override
    public List<OrdenProducto> obtenerOrdenesProductos() throws SQLException{
        return ordenProductoDAO.obtenerOrdenesProductos();
    }
}