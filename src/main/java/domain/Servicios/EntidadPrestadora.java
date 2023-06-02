package domain.Servicios;

import domain.Personas.InteresadoEnServicios;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EntidadPrestadora extends Empresa{
  private String nombre;
  private List<Entidad> entidades;
  private InteresadoEnServicios personaInteresada;
  public EntidadPrestadora(String nombre){
    this.nombre = nombre;
    this.entidades = new ArrayList<>();
  }
  public void agregarEntidad(Entidad entidad){
    entidades.add(entidad);
  }
}
