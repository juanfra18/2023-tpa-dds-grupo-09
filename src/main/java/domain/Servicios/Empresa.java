package domain.Servicios;

import domain.Personas.InteresadoEnServicios;

public abstract class Empresa {
  private String nombre;
  private InteresadoEnServicios persona;

  public void asignarPersona(InteresadoEnServicios persona){
    this.persona = persona;
  }
  public void enviarInformacionAPersona(Informacion informacion){
    //TODO
  }

}
