package services.Localizacion;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name="municipio")
public class Municipio {
    @Id
    public int id;
    @Column(name = "nombre")
    public String nombre;
    @ManyToOne
    @JoinColumn(name="provincia_id",referencedColumnName = "id")
    public Provincia provincia;

    public Municipio(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}