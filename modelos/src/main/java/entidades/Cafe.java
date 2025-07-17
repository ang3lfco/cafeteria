package entidades;

public class Cafe extends Producto{
    private int id;

    public Cafe(){

    }

    public Cafe(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}