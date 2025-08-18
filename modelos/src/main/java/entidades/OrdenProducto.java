package entidades;

public class OrdenProducto {
    private int id;
    private int idOrden;
    private int idProducto;
    private int cantidad;
    private String tamanio;
    
    public OrdenProducto() {
    }

    public OrdenProducto(int idOrden, int idProducto, int cantidad, String tamanio) {
        this.idOrden = idOrden;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.tamanio = tamanio;
    }

    public OrdenProducto(int id, int idOrden, int idProducto, int cantidad, String tamanio) {
        this.id = id;
        this.idOrden = idOrden;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.tamanio = tamanio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }
}