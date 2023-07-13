package domain.Entidades;

import domain.Incidentes.RepositorioDeIncidentes;
import domain.Rankings.EntidadesConMayorCantidadDeIncidentes;
import domain.Rankings.EntidadesQueSolucionanMasLento;
import domain.Rankings.Tierlist;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrganismoDeControl {
  private List<EntidadPrestadora> entidadesPrestadoras;
  private String nombre;
  private String personaMail;
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
  }
}
