package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;
import domain.Persistencia.Converters.FormaDeNotificarAttributeConverter;
import domain.Persistencia.Converters.MedioDeComunicacionAttributeConverter;
import domain.Persistencia.Persistente;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "receptorDeNotificaciones")
public class ReceptorDeNotificaciones extends Persistente {
  @Convert(converter = FormaDeNotificarAttributeConverter.class)
  @Column(name = "formaDeNotificar")
  private FormaDeNotificar formaDeNotificar;
  @Convert(converter = MedioDeComunicacionAttributeConverter.class)
  @Column(name = "medioDeComunicacion")
  private MedioDeComunicacion medioDeComunicacion;
  @Setter
  @Column(name = "mail")
  private String mail;
  @Setter
  @Column(name = "telefono")
  private String telefono;

  public ReceptorDeNotificaciones() {}
  private String getDestinatario(){
    String destinatario = null;

    switch (this.medioDeComunicacion.getClass().getName()){
      case "ViaMail" : destinatario = this.mail;
      case "ViaWPP" : destinatario = this.telefono;
    }
    return destinatario;
  }
  public void recibirNotificacion(ReporteDeIncidente reporteDeIncidente){
    this.formaDeNotificar.recibirNotificacion(this.medioDeComunicacion, reporteDeIncidente, this.getDestinatario());
  }
  public void recibirSolicitudDeRevision(ReporteDeIncidente reporteDeIncidente) {
    this.medioDeComunicacion.recibirNotificacion(reporteDeIncidente.mensaje(), "Solicitud de Revisión de Incidente", this.getDestinatario());
  }
  public void cambiarFormaDeNotificar(FormaDeNotificar forma) {
    this.formaDeNotificar = forma;
  }
  public void cambiarMedioDeComunicacion(MedioDeComunicacion medio) {
    this.medioDeComunicacion = medio;
  }
}
