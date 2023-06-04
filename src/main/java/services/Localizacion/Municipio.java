package services.Localizacion;

import lombok.Getter;

@Getter
public class Municipio extends Localizacion{

    private int idProvincia;
    private int idDepartamento;

    public Municipio(int idProvincia, int idDepartamento,int id) {
        this.idProvincia = idProvincia;
        this.idDepartamento = idDepartamento;
        this.id = id;
    }
}
