package models.domain.Entidades;


import models.domain.Notificaciones.ViaMail;
import models.persistence.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name="organismoDeControl")
public class OrganismoDeControl extends Persistente {
  @OneToMany (cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
  @JoinColumn(name="organismodecontrol_id",referencedColumnName = "id")
  private List<EntidadPrestadora> entidadesPrestadoras;
  @Column(name = "nombre")
  @Setter
  private String nombre;
  @Setter
  @Column(name = "personaMail")
  private String personaMail;
  @Transient
  private ViaMail viaMail;
  public OrganismoDeControl(){
    this.entidadesPrestadoras = new ArrayList<>();
    this.viaMail = new ViaMail();
  }
  public void agregarEntidadPrestadora(EntidadPrestadora entidadPrestadora){
    entidadesPrestadoras.removeIf(entidadPrestadora1 -> entidadPrestadora1.getNombre().equals(entidadPrestadora.getNombre()));
    entidadesPrestadoras.add(entidadPrestadora);
  }

  public void recibirInforme(String ruta,String asunto){
    this.viaMail.recibirArchivos(ruta,asunto,this.personaMail);
  }

  public int cantidadEntidadesPrestadoras(){
    return this.entidadesPrestadoras.size();
  }

  public boolean tieneContacto(){
    return personaMail != null;
  }
}
