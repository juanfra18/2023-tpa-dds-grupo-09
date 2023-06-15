package services.Localizacion;

import lombok.Getter;

@Getter
public class Municipio {
    public int id;
    public String nombre;
    public Provincia provincia;

    public Municipio(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
