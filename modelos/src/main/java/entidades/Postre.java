package entidades;

public class Postre extends Producto{
    private double precio;

    public Postre(){
    }

    public Postre(double precio, String nombre, String imagen){
        super(nombre, imagen);
        this.precio = precio;
    }

    public Postre(int id, double precio, String nombre, String imagen){
        super(id, nombre, imagen);
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}