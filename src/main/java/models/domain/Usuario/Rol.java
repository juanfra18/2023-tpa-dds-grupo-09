package models.domain.Usuario;

import lombok.Getter;
import lombok.Setter;
import models.persistence.Persistente;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="rol")
public class Rol extends Persistente {

  @Column(name = "nombre")
  private String nombre;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo")
  private TipoRol tipo;

  @ManyToMany
  private Set<Permiso> permisos;

  public Rol() {
    this.permisos = new HashSet<>();
  }

  public void agregarPermisos(Permiso ... permisos) {
    Collections.addAll(this.permisos, permisos);
  }

  public boolean tenesPermiso(Permiso permiso) {
    return this.permisos.contains(permiso);
  }

  public boolean tenesPermiso(String nombreInterno) {
    return this.permisos.stream().anyMatch(p -> p.coincideConNombreInterno(nombreInterno));
  }


}
