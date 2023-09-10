package ServicioAPI;


import domain.Personas.Comunidad;

import java.util.ArrayList;
import java.util.Collection;

public class RepoComunidad {

  private Collection<Comunidad> comunidades;

  public RepoComunidad() {
    this.comunidades = new ArrayList<>();
  }

  public void add(Comunidad comunidad) {
    this.comunidades.add(comunidad);
  }

  public void remove(Comunidad comunidad) {
    this.comunidades = this.comunidades.stream().filter(
        x -> !x.getId().equals(
            comunidad.getId())).toList();
  }

  public boolean exists(Long id) {
    return this.comunidades.stream().anyMatch(
        x -> x.getId().equals(id));
  }

  public Comunidad findById(Long id) {
    return this.comunidades.stream().filter(
        x -> x.getId().equals(id)
    ).findFirst().get();
  }

  public Collection<Comunidad> all() {
    return this.comunidades;
  }
}
