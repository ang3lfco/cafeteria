package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import entidades.Producto;
import interfaces.IProductoDAO;

public class ProductoDAO implements IProductoDAO {
    private final ConexionBD conexionBD;

    public ProductoDAO(ConexionBD conexionBD){
        this.conexionBD = conexionBD;
    }

    @Override
    public List<Producto> getProductos() throws SQLException{
        String consulta = "SELECT * FROM Productos";
        Connection conexion = this.conexionBD.crearConexion();
        PreparedStatement pstm = conexion.prepareStatement(consulta);
        ResultSet rs = pstm.executeQuery();
        
        List<Producto> productos = new ArrayList<>();
        while(rs.next()){
            productos.add(new Producto(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("imagen"),
                rs.getInt("idCafe"),
                rs.getInt("idPostre")
            ));
        }
        return productos;
    }
}
