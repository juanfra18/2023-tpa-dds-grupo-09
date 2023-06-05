package services.Localizacion;

import lombok.Getter;

import java.util.Optional;

@Getter
public class Municipio implements Localizacion{
    public int id;
    public String nombre;
    private int provincia;
}
