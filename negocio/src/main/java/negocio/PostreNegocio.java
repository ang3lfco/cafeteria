package negocio;

import java.sql.SQLException;
import java.util.List;

import entidades.Postre;
import interfaces.IPostreDAO;
import interfaces.IPostreNegocio;

public class PostreNegocio implements IPostreNegocio{
    private final IPostreDAO postreDAO;

    public PostreNegocio(IPostreDAO postreDAO){
        this.postreDAO = postreDAO;
    }

    @Override
    public List<Postre> obtenerPostres() throws SQLException{
        return postreDAO.getPostres();
    }

    @Override
    public double obtenerPrecio(int id) throws SQLException{
        return postreDAO.getPrecio(id);
    }
}
