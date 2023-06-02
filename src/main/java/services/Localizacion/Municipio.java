package services.Localizacion;

import lombok.Getter;

@Getter
public class Municipio extends Localizacion{
    public int id;
    public String nombre;
    private int idProvincia;

    public Municipio(int id) {
        this.id = id;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }
}
