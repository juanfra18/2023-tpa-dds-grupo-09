package services.Localizacion;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "provincia")
public class Provincia {
    @Id
    public int id;
    @Column
    public String nombre;

    public Provincia(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}