package domain.Persistencia.Repositorios;


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
import lombok.Getter;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RepositorioDeReportesDeIncidentes implements WithSimplePersistenceUnit {
    private EntityTransaction tx;
    @Getter
    private RepositorioDeIncidentes repositorioDeIncidentes = new RepositorioDeIncidentes();
    //nose si es correcto instanciarlo asi...

    public RepositorioDeReportesDeIncidentes() {
        this.tx = entityManager().getTransaction();
    }

    public static void main(String[] args) {
        /*RepositorioDeReportesDeIncidentes repo = new RepositorioDeReportesDeIncidentes();
        ReporteDeIncidente reporteDeIncidente = new ReporteDeIncidente();

        Provincia jujuy = new RepositorioProvincias().buscar(38);

        Municipio Yavi = new RepositorioDeMunicipios().buscar(386273);

        FormaDeNotificar formaDeNotificar = new CuandoSuceden();
        MedioDeComunicacion medioDeComunicacion = new ViaMail();
        ReceptorDeNotificaciones receptorDeNotificaciones = new ReceptorDeNotificaciones();
        receptorDeNotificaciones.setMail("hola@mail.net");
        receptorDeNotificaciones.setTelefono("+1333333453");
        receptorDeNotificaciones.cambiarFormaDeNotificar(formaDeNotificar);
        receptorDeNotificaciones.cambiarMedioDeComunicacion(medioDeComunicacion);

        MiembroDeComunidad miembroDeComunidad = new RepositorioMiembroDeComunidad().buscar(1L);

        Servicio banio = new RepositorioServicio().buscar(16L);

        RepositorioDeEstablecimientos repositorioDeEstablecimientos = new RepositorioDeEstablecimientos();

        Establecimiento establecimiento = repositorioDeEstablecimientos.buscar(15L);

        RepositorioEntidad repositorioEntidad = new RepositorioEntidad();

        Entidad entidad = repositorioEntidad.buscar(30L);

        reporteDeIncidente.setEstablecimiento(establecimiento);
        reporteDeIncidente.setServicio(banio);
        reporteDeIncidente.setDenunciante(miembroDeComunidad);
        reporteDeIncidente.setObservaciones("Juan tapo el baño");
        reporteDeIncidente.setClasificacion(EstadoIncidente.valueOf("CERRADO"));
        reporteDeIncidente.setEntidad(entidad);
        reporteDeIncidente.setFechaYhora(LocalDateTime.of(2023,8,22,10,10,30));

        repo.agregar(reporteDeIncidente);

        receptorDeNotificaciones.setMail("juapoliXxx");

        //repo.modificar(reporteDeIncidente);

        //System.out.println(repo.buscar(reporteDeIncidente.getId()).getNombre());

        //repo.eliminar(miembroDeComunidad);

        repo.buscarTodos().forEach(reporte -> System.out.println(reporte.getNombre()));

         */
    }

    public void agregar(ReporteDeIncidente reporteDeIncidente) {
        this.tx.begin();
        entityManager().persist(reporteDeIncidente);
        this.tx.commit();
    }

    public void modificar(ReporteDeIncidente reporteDeIncidente) {
        this.tx.begin();
        entityManager().merge(reporteDeIncidente);
        this.tx.commit();
    }

    public void eliminar(ReporteDeIncidente reporteDeIncidente) {
        this.tx.begin();
        entityManager().remove(reporteDeIncidente);
        this.tx.commit();
    }

    public ReporteDeIncidente buscar(long id) {
        return entityManager().find(ReporteDeIncidente.class, id);
    }

    public List<ReporteDeIncidente> buscarTodos() {
        return entityManager().createQuery("from ReporteDeIncidente", ReporteDeIncidente.class).getResultList();
    }
}