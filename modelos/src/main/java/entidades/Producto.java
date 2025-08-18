package entidades;

public abstract class Producto {
    private int id;
    private String nombre;
    private String imagen;
    
    public Producto() {
    }
    
    public Producto(String nombre, String imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }
    
    public Producto(int id, String nombre, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
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
}