package services.Localizacion;

import lombok.Getter;

@Getter
public class Departamento extends Localizacion{
    public int id;
    public String nombre;
    private int idProvincia;
    private int idMunicipio;

    public Departamento(int id) {
        this.id = id;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }
    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }
}
