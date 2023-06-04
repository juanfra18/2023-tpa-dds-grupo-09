package services.Localizacion;

import lombok.Getter;

@Getter
public class Departamento extends Localizacion{

    private int idProvincia;

    public Departamento(int idProvincia,int id) {
        this.idProvincia = idProvincia;
        this.id = id;
    }
}
