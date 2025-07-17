package entidades;

public class Postre extends Producto{
    private int id;
    private double precio;

    public Postre(){
    }

    public Postre(double precio){
        this.precio = precio;
    }

    public Postre(int id, double precio){
        this.id = id;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}