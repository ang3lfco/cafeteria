package entidades;

public class Producto {
    private int id;
    private String nombre;
    private String imagen;
    private int idCafe;
    private int idPostre;
    
    public Producto() {
    }
    
    public Producto(String nombre, String imagen, int idCafe, int idPostre) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.idCafe = idCafe;
        this.idPostre = idPostre;
    }
    
    public Producto(int id, String nombre, String imagen, int idCafe, int idPostre) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.idCafe = idCafe;
        this.idPostre = idPostre;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getIdCafe() {
        return idCafe;
    }

    public void setIdCafe(int idCafe) {
        this.idCafe = idCafe;
    }

    public int getIdPostre() {
        return idPostre;
    }
    
    public void setIdPostre(int idPostre) {
        this.idPostre = idPostre;
    }

    @Override
    public String toString() {
        return "Producto [id=" + id + ", nombre=" + nombre + ", imagen=" + imagen + ", idCafe=" + idCafe + ", idPostre="
                + idPostre + "]";
    }
}