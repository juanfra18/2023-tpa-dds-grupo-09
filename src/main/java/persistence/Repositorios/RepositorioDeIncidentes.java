package persistence.Repositorios;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Entidades.TipoEntidad;
import domain.Entidades.TipoEstablecimiento;
import domain.Incidentes.EstadoIncidente;
import domain.Incidentes.Incidente;
import domain.Incidentes.ReporteDeIncidente;
import domain.Notificaciones.*;
import domain.Personas.MiembroDeComunidad;
import domain.Servicios.Banio;
import domain.Servicios.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.List;

public class RepositorioDeIncidentes extends RepositorioGenerico<Incidente> {
  private static RepositorioDeIncidentes instancia = null;

  private RepositorioDeIncidentes() {
    super(Incidente.class);
  }
  public static  RepositorioDeIncidentes getInstancia() {
    if (instancia == null) {
      instancia = new RepositorioDeIncidentes();
    }
    return instancia;
  }
  public List<Incidente> getIncidentesEstaSemana() { return this.buscarTodos().stream().filter(incidente -> incidente.primeraApertura().dentroDeEstaSemana()).toList(); }

}
