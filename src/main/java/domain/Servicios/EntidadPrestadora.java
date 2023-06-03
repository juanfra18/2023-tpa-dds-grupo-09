package domain.Servicios;

import domain.Personas.InteresadoEnServicios;
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
    entidades.add(entidad);
  }

  @Override
  public void asignarPersona(InteresadoEnServicios persona) {
    super.asignarPersona(persona);
  }

  @Override
  public void enviarInformacionAPersona(Informacion informacion) {
    super.enviarInformacionAPersona(informacion);
  }
}
