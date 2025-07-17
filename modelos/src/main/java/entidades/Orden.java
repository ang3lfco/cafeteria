package entidades;

import java.time.LocalDateTime;

public class Orden {
    private int id;
    private LocalDateTime fechaHora;
    
    public Orden() {
    }

    public Orden(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Orden(int id, LocalDateTime fechaHora) {
        this.id = id;
        this.fechaHora = fechaHora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
