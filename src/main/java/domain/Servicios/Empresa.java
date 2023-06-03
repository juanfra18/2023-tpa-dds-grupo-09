package domain.Servicios;

import domain.Personas.InteresadoEnServicios;
import lombok.Getter;

@Getter
public abstract class Empresa {
  protected String nombre;
  protected InteresadoEnServicios persona;

  public void asignarPersona(InteresadoEnServicios persona){
    this.persona = persona;
  }
  public void enviarInformacionAPersona(Informacion informacion){
    //TODO
  }

}
