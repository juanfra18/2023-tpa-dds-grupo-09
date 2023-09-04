package domain.Entidades;


import domain.Notificaciones.ViaMail;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name="organismoDeControl")
public class OrganismoDeControl {
  @Id
  @GeneratedValue
  private int id;
  @OneToMany
  @JoinColumn(name="entidadPrestadora_id",referencedColumnName = "id")
  private List<EntidadPrestadora> entidadesPrestadoras;
  @Column(name = "nombre")
  private String nombre;
  @Column(name = "personaMail")
  private String personaMail;
  @Transient //TODO
  private ViaMail viaMail;
  public OrganismoDeControl(String nombre, String personaMail){
    this.nombre = nombre;
    this.entidadesPrestadoras = new ArrayList<>();
    this.personaMail = personaMail;
    this.viaMail = new ViaMail(this.personaMail);
  }
  public void agregarEntidadPrestadora(EntidadPrestadora entidadPrestadora){
    entidadesPrestadoras.removeIf(entidadPrestadora1 -> entidadPrestadora1.getNombre().equals(entidadPrestadora.getNombre()));
    entidadesPrestadoras.add(entidadPrestadora);
  }
  public void asignarPersona(String email){
    this.personaMail = email;
    this.viaMail = new ViaMail(personaMail);
  }

  public void recibirInforme(String ruta,String asunto){
    this.viaMail.recibirArchivos(ruta,asunto);
  }
}
