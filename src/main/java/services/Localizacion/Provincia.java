package services.Localizacion;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Setter
@Getter
@Entity
@Table(name = "provincia")
public class Provincia {
    @Id
    public int id;
    @Column(name = "nombre")
    public String nombre;

    public Provincia() {
        //int id, String nombre
        //this.id = id;
        //this.nombre = nombre;
    }
}