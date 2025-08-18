package interfaces;

import java.sql.SQLException;
import java.util.List;

import entidades.Postre;

public interface IPostreDAO {
    List<Postre> getPostres() throws SQLException;
    double getPrecio(int id) throws SQLException;
}
