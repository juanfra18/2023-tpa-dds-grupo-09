package domain.Entidades;


import domain.Notificaciones.ViaMail;
import domain.Persistencia.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name="organismoDeControl")
public class OrganismoDeControl extends Persistente {
  @OneToMany
  @JoinColumn(name="entidadPrestadora_id",referencedColumnName = "id")
  private List<EntidadPrestadora> entidadesPrestadoras;
  @Column(name = "nombre")
  private String nombre;
  @Setter
  @Column(name = "personaMail")
  private String personaMail;
  @Transient //TODO
  private ViaMail viaMail;
  public OrganismoDeControl(String nombre, String personaMail){
    this.nombre = nombre;
    this.entidadesPrestadoras = new ArrayList<>();
    this.personaMail = personaMail;
    this.viaMail = new ViaMail();
  }
  public void agregarEntidadPrestadora(EntidadPrestadora entidadPrestadora){
    entidadesPrestadoras.removeIf(entidadPrestadora1 -> entidadPrestadora1.getNombre().equals(entidadPrestadora.getNombre()));
    entidadesPrestadoras.add(entidadPrestadora);
  }

  public void recibirInforme(String ruta,String asunto){
    this.viaMail.recibirArchivos(ruta,asunto,this.personaMail);
  }
}
