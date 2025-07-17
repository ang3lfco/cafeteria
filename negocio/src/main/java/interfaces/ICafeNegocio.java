package interfaces;

import java.sql.SQLException;
import java.util.List;

import entidades.Cafe;

public interface ICafeNegocio {
    List<Cafe> obtenerCafes() throws SQLException;
}
