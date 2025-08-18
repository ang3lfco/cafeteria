package negocio;

import java.sql.SQLException;
import java.util.List;

import entidades.Orden;
import interfaces.IOrdenDAO;
import interfaces.IOrdenNegocio;

public class OrdenNegocio implements IOrdenNegocio{
    private IOrdenDAO ordenDAO;

    public OrdenNegocio(IOrdenDAO ordenDAO){
        this.ordenDAO = ordenDAO;
    }

    @Override
    public int registrarOrden(Orden orden) throws SQLException{
        return ordenDAO.crear(orden);
    }

    @Override
    public List<Orden> obtenerOrdenes() throws SQLException{
        return ordenDAO.obtenerOrdenes();
    }
}
