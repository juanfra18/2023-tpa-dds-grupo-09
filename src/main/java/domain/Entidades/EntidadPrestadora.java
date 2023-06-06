package domain.Entidades;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EntidadPrestadora extends Empresa{
  private List<Entidad> entidades;
  public EntidadPrestadora(String nombre){
    this.nombre = nombre;
    this.entidades = new ArrayList<>();
  }
  public void agregarEntidad(Entidad entidad){
    entidades.removeIf(entidad1 -> entidad1.getNombre().equals(entidad.getNombre()));
    entidades.add(entidad);
  }
}
