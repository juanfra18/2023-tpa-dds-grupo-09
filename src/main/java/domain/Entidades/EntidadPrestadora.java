package domain.Entidades;


import domain.Notificaciones.ViaMail;
import domain.Persistencia.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@Table(name = "entidadPrestadora")
public class EntidadPrestadora extends Persistente {
  @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name="entidad_id",referencedColumnName = "id")
  private List<Entidad> entidades;
  @Column(name = "nombre")
  @Setter
  private String nombre;
  @Column(name = "personaMail")
  @Setter
  private String personaMail;
  @Transient
  private ViaMail viaMail;
  public EntidadPrestadora(){
    //String nombre, String personaMail
    //this.nombre = nombre;
    this.entidades = new ArrayList<>();
    //this.personaMail = personaMail;
    this.viaMail = new ViaMail();
  }
  public void agregarEntidad(Entidad entidad){
    entidades.removeIf(entidad1 -> entidad1.getNombre().equals(entidad.getNombre()));
    entidades.add(entidad);
  }

  public boolean igualito(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    EntidadPrestadora otro = (EntidadPrestadora) obj;
    return Objects.equals(nombre, otro.nombre);
  }

  public void recibirInforme(String ruta,String asunto){
    this.viaMail.recibirArchivos(ruta,asunto,this.personaMail);
  }
}
