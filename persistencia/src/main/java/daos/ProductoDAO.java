package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public int crearProducto(Producto producto) throws SQLException{
        String consulta = "INSERT INTO Productos(nombre, imagen) VALUES(?,?)";
        Connection conexion = this.conexionBD.crearConexion();
        PreparedStatement pstm = conexion.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
        pstm.setString(1, producto.getNombre());
        pstm.setString(2, producto.getImagen());
        pstm.executeUpdate();
        ResultSet rs = pstm.getGeneratedKeys();
        if(rs.next()){
            return rs.getInt(1);
        }
        return -1;
    }

    @Override
    public List<Producto> getProductos() throws SQLException{
        // String consulta = "SELECT * FROM Productos";
        // Connection conexion = this.conexionBD.crearConexion();
        // PreparedStatement pstm = conexion.prepareStatement(consulta);
        // ResultSet rs = pstm.executeQuery();
        
        // List<Producto> productos = new ArrayList<>();
        // while(rs.next()){
        //     productos.add(new Producto(
        //         rs.getInt("id"),
        //         rs.getString("nombre"),
        //         rs.getString("imagen"),
        //         rs.getInt("idCafe"),
        //         rs.getInt("idPostre")
        //     ));
        // }
        // return productos;
        return null;
    }
}
