package ServicioAPI;

import domain.Entidades.Entidad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RepoEntidad {

  private Collection<Entidad> entidades;

  public RepoEntidad() {
    this.entidades = new ArrayList<>();
  }

  public void add(Entidad entidad) {
    this.entidades.add(entidad);
  }
  public void addAll(List<Entidad> entidades) {
    this.entidades.addAll(entidades);
  }
  public void remove(Entidad entidad) {
    this.entidades = this.entidades.stream().filter(
        x -> !x.getId().equals(
            entidad.getId())).toList();
  }

  public boolean exists(Long id) {
    return this.entidades.stream().anyMatch(
        x -> x.getId().equals(id));
  }

  public Entidad findById(Long id) {
    return this.entidades.stream().filter(
        x -> x.getId().equals(id)
    ).findFirst().get();
  }

  public Collection<Entidad> all() {
    return this.entidades;
  }
}
