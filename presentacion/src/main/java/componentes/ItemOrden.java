package componentes;

import entidades.Producto;

public class ItemOrden{
    private Producto producto;
    private int cantidad;
    private String tamanio;
    
    public ItemOrden(Producto producto, int cantidad, String tamanio) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.tamanio = tamanio;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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