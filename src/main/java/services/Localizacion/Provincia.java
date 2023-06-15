package services.Localizacion;

import lombok.Getter;

@Getter
public class Provincia {
    public int id;
    public String nombre;

    public Provincia(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}