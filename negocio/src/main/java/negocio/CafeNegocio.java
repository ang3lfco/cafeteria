package negocio;

import java.sql.SQLException;
import java.util.List;

import entidades.Cafe;
import interfaces.ICafeDAO;
import interfaces.ICafeNegocio;

public class CafeNegocio implements ICafeNegocio {
    private final ICafeDAO cafeDAO;

    public CafeNegocio(ICafeDAO cafeDAO){
        this.cafeDAO = cafeDAO;
    }

    @Override
    public List<Cafe> obtenerCafes() throws SQLException{
        return cafeDAO.getCafes();
    }
}
