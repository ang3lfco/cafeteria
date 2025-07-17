package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import conexion.ConexionBD;
import entidades.Postre;
import interfaces.IPostreDAO;

public class PostreDAO implements IPostreDAO{
    private final ConexionBD conexionBD;

    public PostreDAO(ConexionBD conexionBD){
        this.conexionBD = conexionBD;
    }

    @Override
    public List<Postre> getPostres() throws SQLException {
        String consulta = "SELECT id, nombre, precio, imagen FROM postres";
        Connection conexion = this.conexionBD.crearConexion();
        PreparedStatement pstm = conexion.prepareStatement(consulta);
        ResultSet rs = pstm.executeQuery();
        
        // List<Postre> postres = new ArrayList<>();
        // while(rs.next()){
        //     postres.add(new Postre(
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

    @Override
    public double getPrecio(int id) throws SQLException{
        String consulta = "SELECT precio FROM Postres WHERE id = ?";
        Connection conexion = this.conexionBD.crearConexion();
        PreparedStatement pstm = conexion.prepareStatement(consulta);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();

        if(rs.next()){
            return rs.getDouble("precio");
        }
        else{
            throw new SQLException("No se encontro el precio.");
        }
    }
}
