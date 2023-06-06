package domain.Entidades;

import domain.Personas.MiembroDeComunidad;
import domain.Personas.Informacion;
import lombok.Getter;

@Getter
public abstract class Empresa {
  protected String nombre;
  protected MiembroDeComunidad persona;

  public void asignarPersona(MiembroDeComunidad persona){
    this.persona = persona;
  }
  public void enviarInformacionAPersona(Informacion informacion){
    //TODO
  }

}
