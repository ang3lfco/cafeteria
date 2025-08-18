package entidades;

public class TamanioCafe {
    private int id;
    private double precio;
    private int idCafe;
    private int idTamanio;
    
    public TamanioCafe() {
    }

    public TamanioCafe(double precio, int idCafe, int idTamanio) {
        this.precio = precio;
        this.idCafe = idCafe;
        this.idTamanio = idTamanio;
    }

    public TamanioCafe(int id, double precio, int idCafe, int idTamanio) {
        this.id = id;
        this.precio = precio;
        this.idCafe = idCafe;
        this.idTamanio = idTamanio;
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

    public int getIdCafe() {
        return idCafe;
    }

    public void setIdCafe(int idCafe) {
        this.idCafe = idCafe;
    }

    public int getIdTamanio() {
        return idTamanio;
    }

    public void setIdTamanio(int idTamanio) {
        this.idTamanio = idTamanio;
    }
}
