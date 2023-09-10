package ServicioAPI;

import domain.Incidentes.Incidente;

import java.util.ArrayList;
import java.util.Collection;

public class RepoIncidente {

  private Collection<Incidente> incidentes;

  public RepoIncidente() {
    this.incidentes = new ArrayList<>();
  }

  public void add(Incidente incidente) {
    this.incidentes.add(incidente);
  }

  public void remove(Incidente incidente) {
    this.incidentes = this.incidentes.stream().filter(
        x -> !x.getId().equals(
            incidente.getId())).toList();
  }

  public boolean exists(Long id) {
    return this.incidentes.stream().anyMatch(
        x -> x.getId().equals(id));
  }

  public Incidente findById(Long id) {
    return this.incidentes.stream().filter(
        x -> x.getId().equals(id)
    ).findFirst().get();
  }

  public Collection<Incidente> all() {
    return this.incidentes;
  }
}
