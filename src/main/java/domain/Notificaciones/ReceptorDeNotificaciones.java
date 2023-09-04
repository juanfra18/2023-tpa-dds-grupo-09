package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;
import domain.Persistencia.Persistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "receptorDeNotificaciones")
public class ReceptorDeNotificaciones extends Persistente {
  @Transient
  private FormaDeNotificar formaDeNotificar;
  @Transient
  private MedioDeComunicacion medioDeComunicacion;
  @Column(name = "mail")
  private String mail;
  @Column(name = "telefono")
  private String telefono;

  public ReceptorDeNotificaciones(MedioDeComunicacion medioDeComunicacion, FormaDeNotificar formaDeNotificar ,String mail, String telefono) {
    this.cambiarMedioDeComunicacion(medioDeComunicacion);
    this.cambiarFormaDeNotificar(formaDeNotificar);
    this.mail = mail;
    this.telefono = telefono;
  }
  public void recibirNotificacion(ReporteDeIncidente reporteDeIncidente){
    this.formaDeNotificar.recibirNotificacion(reporteDeIncidente);
  }
  public void recibirSolicitudDeRevision(ReporteDeIncidente reporteDeIncidente) {
    this.medioDeComunicacion.recibirNotificacion(reporteDeIncidente.mensaje(), "Solicitud de Revisión de Incidente");
  }
  public void cambiarFormaDeNotificar(FormaDeNotificar forma) {
    this.formaDeNotificar = forma;
  }
  public void cambiarMedioDeComunicacion(MedioDeComunicacion medio) {
    this.medioDeComunicacion = medio;
  }
}
