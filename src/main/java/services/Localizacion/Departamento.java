package services.Localizacion;

import lombok.Getter;

@Getter
public class Departamento implements Localizacion{
    public int id;
    public String nombre;
    private Provincia provincia;

}
