package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import entidades.OrdenProducto;
import interfaces.IOrdenProductoDAO;

public class OrdenProductoDAO implements IOrdenProductoDAO{
    private ConexionBD conexionBD;

    public OrdenProductoDAO(ConexionBD conexionBD){
        this.conexionBD = conexionBD;
    }

    @Override
    public void crear(OrdenProducto ordenProducto) throws SQLException{
        String consulta = "INSERT INTO OrdenesProductos(idOrden, idProducto, cantidad, tamaño) VALUES(?,?,?,?)";
        Connection conexion = this.conexionBD.crearConexion();
        PreparedStatement pstm = conexion.prepareStatement(consulta);
        pstm.setInt(1, ordenProducto.getIdOrden());
        pstm.setInt(2, ordenProducto.getIdProducto());
        pstm.setInt(3, ordenProducto.getCantidad());
        pstm.setString(4, ordenProducto.getTamanio());
        pstm.executeUpdate();
        pstm.close();
        conexion.close();
    }

    @Override
    public List<OrdenProducto> obtenerOrdenesProductos() throws SQLException{
        String consulta = "SELECT * FROM OrdenesProductos";
        Connection conexion = this.conexionBD.crearConexion();
        PreparedStatement pstm = conexion.prepareStatement(consulta);
        ResultSet rs = pstm.executeQuery();

        List<OrdenProducto> ordenesProductos = new ArrayList<>();
        while(rs.next()){
            OrdenProducto ordenProducto = new OrdenProducto(
                rs.getInt("id"),
                rs.getInt("idOrden"),
                rs.getInt("idProducto"),
                rs.getInt("cantidad"),
                rs.getString("tamaño")
            );
            ordenesProductos.add(ordenProducto);
        }

        rs.close();
        pstm.close();
        conexion.close();
        return ordenesProductos;
    }
}
