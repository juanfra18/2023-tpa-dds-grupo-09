package domain.Entidades;

import domain.Personas.MiembroDeComunidad;
import domain.Personas.Informacion;
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
  public void asignarPersona(MiembroDeComunidad persona) {
    super.asignarPersona(persona);
  }

  @Override
  public void enviarInformacionAPersona(Informacion informacion) {
    super.enviarInformacionAPersona(informacion);
  }
}
