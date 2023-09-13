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

public class RepositorioDeIncidentes implements WithSimplePersistenceUnit {
  private List<Incidente> incidentes; //TODO no se está usando
  private EntityTransaction tx;
  private static RepositorioDeIncidentes instancia = null;

  public RepositorioDeIncidentes() {
    tx = entityManager().getTransaction();
  }
  public static  RepositorioDeIncidentes getInstancia() {
    if (instancia == null) {
      instancia = new RepositorioDeIncidentes();
    }
    return instancia;
  }

  public static void main(String[] args) { // Se pasa por parametro o se instancian
    RepositorioDeReportesDeIncidentes repositorioDeReportesDeIncidentes = RepositorioDeReportesDeIncidentes.getInstancia();
    ReporteDeIncidente reporteDeIncidente = new ReporteDeIncidente();
    ReporteDeIncidente reporteDeIncidente2 = new ReporteDeIncidente();
    ReporteDeIncidente reporteDeIncidente3 = new ReporteDeIncidente();
    ReporteDeIncidente reporteDeIncidente4 = new ReporteDeIncidente();
    RepositorioMiembroDeComunidad repositorioMiembroDeComunidad = RepositorioMiembroDeComunidad.getInstancia();


    Provincia jujuy = new Provincia();
    jujuy.setId(38);
    jujuy.setNombre("Jujuy");
    Municipio Yavi = new Municipio();
    Yavi.setId(386273);
    Yavi.setNombre("Yavi");
    Yavi.setProvincia(jujuy);

    FormaDeNotificar formaDeNotificar = new CuandoSuceden();
    MedioDeComunicacion medioDeComunicacion = new ViaMail();
    ReceptorDeNotificaciones receptorDeNotificaciones = new ReceptorDeNotificaciones();
    receptorDeNotificaciones.setMail("hola@mail.net");
    receptorDeNotificaciones.setTelefono("+1333333453");
    receptorDeNotificaciones.cambiarFormaDeNotificar(formaDeNotificar);
    receptorDeNotificaciones.cambiarMedioDeComunicacion(medioDeComunicacion);

    MiembroDeComunidad miembroDeComunidad = new MiembroDeComunidad();
    miembroDeComunidad.setNombre("pablo");
    miembroDeComunidad.setApellido("perez");
    miembroDeComunidad.setReceptorDeNotificaciones(receptorDeNotificaciones);
    miembroDeComunidad.setRepositorioDeReportesDeIncidentes(repositorioDeReportesDeIncidentes);

    repositorioMiembroDeComunidad.agregar(miembroDeComunidad);

    Servicio banio = new Banio();
    banio.setTipo("DAMAS");

    Establecimiento establecimiento = new Establecimiento();
    establecimiento.setLocalizacion(Yavi);
    establecimiento.setNombre("Yavi");
    establecimiento.setTipoEstablecimiento(TipoEstablecimiento.valueOf("ESTACION"));
    establecimiento.agregarServicio(banio);

    Entidad entidad = new Entidad();
    entidad.setTipoEntidad(TipoEntidad.valueOf("FERROCARRIL"));
    entidad.setNombre("Mitre");
    entidad.agregarEstablecimiento(establecimiento);


    reporteDeIncidente.setEstablecimiento(establecimiento);
    reporteDeIncidente.setServicio(banio);
    reporteDeIncidente.setDenunciante(miembroDeComunidad);
    reporteDeIncidente.setObservaciones("Juan tapo el baño");
    reporteDeIncidente.setClasificacion(EstadoIncidente.valueOf("ABIERTO"));
    reporteDeIncidente.setEntidad(entidad);
    reporteDeIncidente.setFechaYhora(LocalDateTime.of(2023,10,1,10,10,30));

    reporteDeIncidente2.setEstablecimiento(establecimiento);
    reporteDeIncidente2.setServicio(banio);
    reporteDeIncidente2.setDenunciante(miembroDeComunidad);
    reporteDeIncidente2.setObservaciones("Juan tapo el baño");
    reporteDeIncidente2.setClasificacion(EstadoIncidente.valueOf("ABIERTO"));
    reporteDeIncidente2.setEntidad(entidad);
    reporteDeIncidente2.setFechaYhora(LocalDateTime.of(2023,10,2,12,10,30));

    reporteDeIncidente3.setEstablecimiento(establecimiento);
    reporteDeIncidente3.setServicio(banio);
    reporteDeIncidente3.setDenunciante(miembroDeComunidad);
    reporteDeIncidente3.setObservaciones("Baño destapado");
    reporteDeIncidente3.setClasificacion(EstadoIncidente.valueOf("CERRADO"));
    reporteDeIncidente3.setEntidad(entidad);
    reporteDeIncidente3.setFechaYhora(LocalDateTime.of(2023,10,3,12,10,30));

    reporteDeIncidente4.setEstablecimiento(establecimiento);
    reporteDeIncidente4.setServicio(banio);
    reporteDeIncidente4.setDenunciante(miembroDeComunidad);
    reporteDeIncidente4.setObservaciones("Juan tapo el baño denuevo");
    reporteDeIncidente4.setClasificacion(EstadoIncidente.valueOf("ABIERTO"));
    reporteDeIncidente4.setEntidad(entidad);
    reporteDeIncidente4.setFechaYhora(LocalDateTime.of(2023,10,3,14,10,30));

    repositorioDeReportesDeIncidentes.agregar(reporteDeIncidente);
    repositorioDeReportesDeIncidentes.agregar(reporteDeIncidente2);
    repositorioDeReportesDeIncidentes.agregar(reporteDeIncidente3);
    repositorioDeReportesDeIncidentes.agregar(reporteDeIncidente4);
  }
    public List<Incidente> getIncidentesEstaSemana() {
        return this.buscarTodos().stream().filter(incidente -> incidente.primeraApertura().dentroDeEstaSemana()).toList();
    }

  public void agregar(Incidente incidente) {
    this.tx.begin();
    entityManager().persist(incidente);
    this.tx.commit();
  }

  public void modificar(Incidente incidente) {
    this.tx.begin();
    entityManager().merge(incidente);
    this.tx.commit();
  }
  public void eliminar(Incidente incidente) {
    this.tx.begin();
    entityManager().remove(incidente);
    this.tx.commit();
  }

  public Incidente buscar(long id) {
    return entityManager().find(Incidente.class, id);
  }

  public List<Incidente> buscarTodos() {
    return entityManager().createQuery("from Incidente", Incidente.class).getResultList();
  }
}
