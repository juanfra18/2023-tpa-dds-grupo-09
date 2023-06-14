package services.Localizacion;

import lombok.Getter;

@Getter
public class Municipio {
    public int id;
    public String nombre;
    public Provincia provincia;
}
