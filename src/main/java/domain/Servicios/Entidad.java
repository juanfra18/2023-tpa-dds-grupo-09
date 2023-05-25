package domain.Servicios;

import domain.Personas.InteresadoEnServicios;
import services.Localizacion.Localizacion;

import java.util.List;

public class Entidad {
  private String nombre;
  private TipoEntidad tipoEntidad;
  private List<Establecimiento> establecimientos;
  private Localizacion localizacion;
  private InteresadoEnServicios persona;
  public Entidad(String nombre, String tipoEntidad){
    this.nombre = nombre;
    this.tipoEntidad = TipoEntidad.valueOf(tipoEntidad);
  }
  public void asignarPersona(InteresadoEnServicios persona){
    this.persona = persona;
  }
}
