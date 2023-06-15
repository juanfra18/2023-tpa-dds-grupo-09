package domain.Entidades;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    EntidadPrestadora otro = (EntidadPrestadora) obj;
    return Objects.equals(nombre, otro.nombre);
  }
}
