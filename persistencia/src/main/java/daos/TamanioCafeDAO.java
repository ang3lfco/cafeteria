package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.ConexionBD;
import interfaces.ITamanioCafeDAO;

public class TamanioCafeDAO implements ITamanioCafeDAO{
    private ConexionBD conexionBD;

    public TamanioCafeDAO(ConexionBD conexionBD){
        this.conexionBD = conexionBD;
    }

    @Override
    public double getPrecio(int idCafe, int idTamanio) throws SQLException{
        String consulta = "SELECT precio FROM TamañosCafes WHERE idCafe = ? AND idTamaño = ?";
        Connection conexion = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try{
            conexion = this.conexionBD.crearConexion();
            pstm = conexion.prepareStatement(consulta);
            pstm.setInt(1, idCafe);
            pstm.setInt(2, idTamanio);
            rs = pstm.executeQuery();

            if(rs.next()){
                return rs.getDouble("precio");
            }
            else{
                throw new SQLException("E1. No se encontro el precio.");
            }
        }
        catch(SQLException e){
            throw new SQLException("E2. No se encontro el precio.");
        }
        finally{
            rs.close();
            pstm.close();
            conexion.close();
        }
    }
}
