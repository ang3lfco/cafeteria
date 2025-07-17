package negocio;

import java.sql.SQLException;

import interfaces.ITamanioCafeDAO;
import interfaces.ITamanioCafeNegocio;

public class TamanioCafeNegocio implements ITamanioCafeNegocio{
    private ITamanioCafeDAO tamanioCafeDAO;

    public TamanioCafeNegocio(ITamanioCafeDAO tamanioCafeDAO){
        this.tamanioCafeDAO = tamanioCafeDAO;
    }

    @Override
    public double obtenerPrecioCafe(int idCafe, int idTamanio) throws SQLException{
        return tamanioCafeDAO.getPrecio(idCafe, idTamanio);
    }
}
