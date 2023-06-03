package services.Localizacion;

import lombok.Getter;

@Getter
public class Provincia extends  Localizacion{
    public int id;
    public String nombre;

    public Provincia(int id) {
        this.id = id;
    }
}
