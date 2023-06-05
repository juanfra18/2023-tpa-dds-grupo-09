package domain.Entidades;

import domain.Personas.InteresadoEnServicios;
import domain.Personas.Informacion;
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
