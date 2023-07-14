package domain.Entidades;


import domain.Notificaciones.ViaMail;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class EntidadPrestadora {
  private List<Entidad> entidades;
  private String nombre;
  private String personaMail;
  private ViaMail viaMail;
  public EntidadPrestadora(String nombre, String personaMail){
    this.nombre = nombre;
    this.entidades = new ArrayList<>();
    this.personaMail = personaMail;
    this.viaMail = new ViaMail(this.personaMail);
  }
  public void agregarEntidad(Entidad entidad){
    entidades.removeIf(entidad1 -> entidad1.getNombre().equals(entidad.getNombre()));
    entidades.add(entidad);
  }
  public void asignarPersona(String email){
    this.personaMail = email;
    this.viaMail = new ViaMail(email);
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

  public void recibirInforme(String ruta,String asunto){
    this.viaMail.recibirArchivos(ruta,asunto);
  }
}
