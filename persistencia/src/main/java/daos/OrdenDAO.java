package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import entidades.Orden;
import interfaces.IOrdenDAO;

public class OrdenDAO implements IOrdenDAO{
    private ConexionBD conexionBD;

    public OrdenDAO(ConexionBD conexionBD){
        this.conexionBD = conexionBD;
    }

    @Override
    public int crear(Orden orden) throws SQLException{
        String consulta = "INSERT INTO Ordenes(fechaHora) VALUES(?)";
        Connection conexion = this.conexionBD.crearConexion();
        PreparedStatement pstm = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
        pstm.setTimestamp(1, Timestamp.valueOf(orden.getFechaHora()));
        pstm.executeUpdate();
        ResultSet rs = pstm.getGeneratedKeys();
        int idGenerado = 0;
        if(rs.next()){
            idGenerado = rs.getInt(1);
        }
        pstm.close();
        conexion.close();
        return idGenerado;
    }

    @Override
    public List<Orden> obtenerOrdenes() throws SQLException{
        String consulta = "SELECT * FROM Ordenes ORDER BY fechaHora DESC LIMIT 10";
        Connection conexion = this.conexionBD.crearConexion();
        PreparedStatement pstm = conexion.prepareStatement(consulta);
        ResultSet rs = pstm.executeQuery();

        List<Orden> ordenes = new ArrayList<>();
        while(rs.next()){
            Orden orden = new Orden(
                rs.getInt("id"),
                rs.getTimestamp("fechaHora").toLocalDateTime()
            );
            ordenes.add(orden);
        }

        rs.close();
        pstm.close();
        conexion.close();
        return ordenes;
    }
}
