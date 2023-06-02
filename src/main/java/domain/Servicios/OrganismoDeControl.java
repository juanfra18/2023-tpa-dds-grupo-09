package domain.Servicios;

import lombok.Getter;

import java.util.List;

@Getter
public class OrganismoDeControl extends Empresa{
  private String nombre;
  private List<EntidadPrestadora> entidadesPrestadoras;
  public OrganismoDeControl(String nombre){
    this.nombre = nombre;
  }

  public void agregarEntidadPrestadora(EntidadPrestadora entidadPrestadora){entidadesPrestadoras.add(entidadPrestadora);}
}
