package domain.Servicios;

import lombok.Getter;

@Getter
public class OrganismoDeControl extends Empresa{
  private String nombre;
  public OrganismoDeControl(String nombre){
    this.nombre = nombre;
  }
}
