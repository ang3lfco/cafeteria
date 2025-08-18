package entidades;

public class Tamanio {
    private int id;
    private String tamanio;

    public Tamanio(){
    }

    public Tamanio(String tamanio){
        this.tamanio = tamanio;
    }

    public Tamanio(int id, String tamanio){
        this.id = id;
        this.tamanio = tamanio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }
}
