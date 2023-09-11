package domain.Personas;

import domain.Persistencia.Persistente;
import domain.Servicios.Servicio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "parServicioRol")
public class ParServicioRol extends Persistente {
    @ManyToOne
    @JoinColumn(name = "servicio_id", referencedColumnName = "id")
    private Servicio servicio;
    @Enumerated(EnumType.STRING)
    private Rol rol;

    public ParServicioRol() {}

    public void cambiarRol(){
        if(this.rol == Rol.OBSERVADOR)
            this.rol = Rol.AFECTADO;
        else
            this.rol = Rol.OBSERVADOR;
    }
}
