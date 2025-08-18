package interfaces;

import java.sql.SQLException;
import java.util.List;

import entidades.Cafe;

public interface ICafeDAO {
    List<Cafe> getCafes() throws SQLException;
}
