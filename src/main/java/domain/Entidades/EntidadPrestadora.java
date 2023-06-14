package domain.Entidades;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EntidadPrestadora {
  private List<Entidad> entidades;
  private String nombre;
  private String personaMail;
  public EntidadPrestadora(String nombre){
    this.nombre = nombre;
    this.entidades = new ArrayList<>();
  }
  public void agregarEntidad(Entidad entidad){
    entidades.removeIf(entidad1 -> entidad1.getNombre().equals(entidad.getNombre()));
    entidades.add(entidad);
  }
  public void asignarPersona(String email){
    this.personaMail = email;
  }
}
