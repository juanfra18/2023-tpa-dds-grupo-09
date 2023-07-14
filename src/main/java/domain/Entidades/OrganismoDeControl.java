package domain.Entidades;


import domain.Notificaciones.ViaMail;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrganismoDeControl {
  private List<EntidadPrestadora> entidadesPrestadoras;
  private String nombre;
  private String personaMail;
  private ViaMail viaMail;
  public OrganismoDeControl(String nombre){
    this.nombre = nombre;
    this.entidadesPrestadoras = new ArrayList<>();
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
