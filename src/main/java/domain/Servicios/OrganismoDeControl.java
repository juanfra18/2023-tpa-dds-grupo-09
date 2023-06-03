package domain.Servicios;

import domain.Personas.InteresadoEnServicios;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrganismoDeControl extends Empresa {
  private List<EntidadPrestadora> entidadesPrestadoras;
  public OrganismoDeControl(String nombre){
    this.nombre = nombre;
    this.entidadesPrestadoras = new ArrayList<>();
  }

  public void agregarEntidadPrestadora(EntidadPrestadora entidadPrestadora){entidadesPrestadoras.add(entidadPrestadora);}

  @Override
  public void asignarPersona(InteresadoEnServicios persona) {
    super.asignarPersona(persona);
  }

  @Override
  public void enviarInformacionAPersona(Informacion informacion) {
    super.enviarInformacionAPersona(informacion);
  }
}
