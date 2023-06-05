package domain.Entidades;

import domain.Personas.InteresadoEnServicios;
import domain.Personas.Informacion;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrganismoDeControl extends Empresa {
  private List<EntidadPrestadora> entidadesPrestadoras;
  public OrganismoDeControl(String nombre){
    this.nombre = nombre;
    this.entidadesPrestadoras = new ArrayList<>();
  }
  public void agregarEntidadPrestadora(EntidadPrestadora entidadPrestadora){entidadesPrestadoras.add(entidadPrestadora);}
}
