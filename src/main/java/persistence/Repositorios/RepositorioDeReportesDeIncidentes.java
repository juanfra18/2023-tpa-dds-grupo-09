package persistence.Repositorios;


import domain.Incidentes.ReporteDeIncidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Getter;

import javax.persistence.EntityTransaction;
import java.util.List;

@Getter
public class RepositorioDeReportesDeIncidentes implements WithSimplePersistenceUnit {

    private EntityTransaction tx;
    private static RepositorioDeReportesDeIncidentes instancia = null;
    @Getter
    private RepositorioDeIncidentes repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia(); //TODO nose si es correcto instanciarlo asi...


    private RepositorioDeReportesDeIncidentes() {
        tx = entityManager().getTransaction();
    }
    public static  RepositorioDeReportesDeIncidentes getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioDeReportesDeIncidentes();
        }
        return instancia;
    }

    public static void main(String[] args) {
        /*RepositorioDeReportesDeIncidentes repo = new RepositorioDeReportesDeIncidentes();
        ReporteDeIncidente reporteDeIncidente = new ReporteDeIncidente();

        Provincia jujuy = RepositorioProvincias.getInstancia().buscar(38);

        Municipio Yavi = RepositorioDeMunicipios.getInstancia().buscar(386273);

        FormaDeNotificar formaDeNotificar = new CuandoSuceden();
        MedioDeComunicacion medioDeComunicacion = new ViaMail();
        ReceptorDeNotificaciones receptorDeNotificaciones = new ReceptorDeNotificaciones();
        receptorDeNotificaciones.setMail("hola@mail.net");
        receptorDeNotificaciones.setTelefono("+1333333453");
        receptorDeNotificaciones.cambiarFormaDeNotificar(formaDeNotificar);
        receptorDeNotificaciones.cambiarMedioDeComunicacion(medioDeComunicacion);

        MiembroDeComunidad miembroDeComunidad = RepositorioMiembroDeComunidad.getInstancia().buscar(1L);

        Servicio banio = RepositorioServicio.getInstancia().buscar(16L);

        RepositorioDeEstablecimientos repositorioDeEstablecimientos = RepositorioDeEstablecimientos.getInstancia();

        Establecimiento establecimiento = repositorioDeEstablecimientos.buscar(15L);

        RepositorioEntidad repositorioEntidad = RepositorioEntidad.getInstancia();

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