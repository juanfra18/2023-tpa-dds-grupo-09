package domain.Personas;

public class OrganismoDeControl {
  private String nombre;
  private InteresadoEnServicios persona;
  public OrganismoDeControl(String nombre){
    this.nombre = nombre;
  }
  public void asignarPersona(InteresadoEnServicios persona){
    this.persona = persona;
  }
}
