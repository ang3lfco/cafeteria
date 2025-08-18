package entidades;

public class Cafe extends Producto{

    public Cafe(){

    }

    public Cafe(int id, String nombre, String imagen){
        super(id, nombre, imagen);
    }

    public Cafe(String nombre, String imagen){
        super(nombre, imagen);
    }
}