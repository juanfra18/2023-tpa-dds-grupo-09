package domain.Servicios;

import domain.Personas.InteresadoEnServicios;
import lombok.Getter;
import services.Localizacion.Localizacion;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Entidad {
  private String nombre;
  private TipoEntidad tipoEntidad;
  private List<Establecimiento> establecimientos;
  private Localizacion localizacion;
  private InteresadoEnServicios persona;
  public Entidad(String nombre, String tipoEntidad){
    this.nombre = nombre;
    this.tipoEntidad = TipoEntidad.valueOf(tipoEntidad);
    this.establecimientos = new ArrayList<>();
  }
  public void agregarEstablecimiento(Establecimiento establecimiento){
    establecimientos.add(establecimiento);
  }
  public void asignarPersona(InteresadoEnServicios persona){
    this.persona = persona;
  }
  public void enviarInformacionAPersona(Informacion informacion){
    //TODO
  }
}
