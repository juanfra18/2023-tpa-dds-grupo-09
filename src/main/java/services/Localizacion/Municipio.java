package services.Localizacion;

import lombok.Getter;

@Getter
public class Municipio extends Localizacion{
    public int id;
    public String nombre;
    private int idProvincia;
    private int idDepartamento;

    public Municipio(int idProvincia, int idDepartamento,int id) {
        this.idProvincia = idProvincia;
        this.idDepartamento = idDepartamento;
        this.id = id;
    }
}
