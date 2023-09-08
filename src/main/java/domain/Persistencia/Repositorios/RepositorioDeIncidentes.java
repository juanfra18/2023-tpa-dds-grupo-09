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
import domain.Servicios.Elevacion;
import domain.Servicios.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Getter;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class RepositorioDeIncidentes implements WithSimplePersistenceUnit {
    private List<Incidente> incidentes;
    private EntityTransaction tx;

    public RepositorioDeIncidentes() {
        incidentes = new ArrayList<>();
        this.tx = entityManager().getTransaction();
    }

    public static void main(String[] args) {
        RepositorioDeIncidentes repo = new RepositorioDeIncidentes();
        ReporteDeIncidente reporteDeIncidente = new ReporteDeIncidente();

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
        miembroDeComunidad.setRepositorioDeIncidentes(repo);


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
        reporteDeIncidente.setFechaYhora(LocalDateTime.of(2023,8,22,10,10,30));

        repo.agregar(reporteDeIncidente);

        receptorDeNotificaciones.setMail("juapoliXxx");

        repo.modificar(reporteDeIncidente);

        System.out.println(repo.buscar(reporteDeIncidente.getId()).getDenunciante());

        //repo.eliminar(miembroDeComunidad);

        repo.buscarTodos().forEach(reporte -> System.out.println(reporte.getEstablecimiento()));
    }


    public void registrarIncidente(ReporteDeIncidente reporteDeIncidente) {
        List<Incidente> incidentesSobreLaMismaProblematica = this.incidentes.stream().filter(i -> i.getEstablecimiento().igualito(reporteDeIncidente.getEstablecimiento()) && i.getServicio().igualito(reporteDeIncidente.getServicio())).toList();
        Incidente mismoIncidente = null;

        if (!incidentesSobreLaMismaProblematica.isEmpty()) {
            mismoIncidente = incidentesSobreLaMismaProblematica.get(incidentesSobreLaMismaProblematica.size() - 1);
        }
        //para obtener el mismo incidente pero el mas reciente

        if (mismoIncidente != null && !mismoIncidente.cerrado()) {
            if (reporteDeIncidente.esDeCierre()) { //hay q cerrar el reporte y ver si no hay otros tmb sin cerrar
                mismoIncidente.setReporteDeCierre(reporteDeIncidente);
            } else { //es del mismo incidente, pero no de cierre
                mismoIncidente.agregarReporteDeApertura(reporteDeIncidente);
            }
        } else { // No es del mismo incidente y no es de cierre
            if (!reporteDeIncidente.esDeCierre()) {
                Incidente incidente = new Incidente(reporteDeIncidente.getEstablecimiento(), reporteDeIncidente.getServicio());
                incidente.agregarReporteDeApertura(reporteDeIncidente);
                this.incidentes.add(incidente);
            }
        }
    }

    public List<Incidente> getIncidentesEstaSemana() {
        return incidentes.stream().filter(incidente -> incidente.primeraApertura().dentroDeEstaSemana()).toList();
    }

    private void agregar(ReporteDeIncidente reporteDeIncidente) {
        this.tx.begin();
        registrarIncidente(reporteDeIncidente);
        entityManager().persist(reporteDeIncidente);
        this.tx.commit();
    }

    private void modificar(ReporteDeIncidente reporteDeIncidente) {
        this.tx.begin();
        entityManager().merge(reporteDeIncidente);
        this.tx.commit();
    }

    private void eliminar(ReporteDeIncidente reporteDeIncidente) {
        this.tx.begin();
        entityManager().remove(reporteDeIncidente);
        this.tx.commit();
    }

    private ReporteDeIncidente buscar(long id) {
        return entityManager().find(ReporteDeIncidente.class, id);
    }

    private List<ReporteDeIncidente> buscarTodos() {
        return entityManager().createQuery("from ReporteDeIncidente", ReporteDeIncidente.class).getResultList();
    }
}