package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import conexion.ConexionBD;
import entidades.Cafe;
import interfaces.ICafeDAO;

public class CafeDAO implements ICafeDAO {
    private final ConexionBD conexionBD;

    public CafeDAO(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public List<Cafe> getCafes() throws SQLException {
        String consulta = "SELECT id, nombre, precio, imagen FROM cafes";
        Connection conexion = this.conexionBD.crearConexion();
        PreparedStatement pstm = conexion.prepareStatement(consulta);
        ResultSet rs = pstm.executeQuery();

        // List<Cafe> cafes = new ArrayList<>();
        // while(rs.next()){
        //     cafes.add(new Cafe(
        //         rs.getInt("id"), 
        //         rs.getString("nombre"), 
        //         rs.getDouble("precio"),
        //         rs.getString("imagen")
        //     ));
        // }
        
        rs.close();
        pstm.close();
        conexion.close();
        return null;
    }
}
