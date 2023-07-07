package domain.Notificaciones;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Incidentes.ReporteDeIncidente;
import domain.Personas.MiembroDeComunidad;
import domain.Servicios.Banio;
import domain.Servicios.Servicio;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import services.Localizacion.Municipio;

import java.sql.Time;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;

public class ViaMailJavax implements AdapterViaMail{
  private String remitente = "disenioGrupo9@gmail.com";
  private String claveemail = "caMbon-xuwxan-1ruzvi";
  private String asunto;
  private String cuerpo;

  public void recibirNotificacion(ReporteDeIncidente reporteDeIncidente, String mailDestinatario){
    asunto = "Nuevo reporte de incidente";
    cuerpo = "Un miembro de una de las comunidades de la que usted forma parte ha reportado un incidente" +
             " en " + reporteDeIncidente.getEstablecimiento() + " sobre el servicio de " + reporteDeIncidente.getServicio() +
             ". \n Observaciones: " + reporteDeIncidente.getObservaciones();


    Properties props = System.getProperties();
    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
    props.put("mail.smtp.user", remitente);
    props.put("mail.smtp.clave", claveemail);    //La clave de la cuenta
    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
    props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

    Session session = Session.getInstance(props, new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(remitente, claveemail);
      }
    });

    jakarta.mail.internet.MimeMessage message = new MimeMessage(session);

    try {
      message.setFrom(new jakarta.mail.internet.InternetAddress(remitente));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailDestinatario));   //Se podrían añadir varios de la misma manera
      message.setSubject(asunto);
      message.setText(cuerpo);
      Transport.send(message);
    }
    catch (MessagingException me) {
      me.printStackTrace();   //Si se produce un error
    }
  }
  public static void main(String[] args) {
    ViaMailJavax mailJavax = new ViaMailJavax();
    MiembroDeComunidad miembroDeComunidad = new MiembroDeComunidad("Paoli", "Juan", "juanpaoli@gmail.com", "3442560890");
    Servicio servicio = new Banio("UNISEX");
    Entidad entidad = new Entidad("Jorge", "ESTABLECIMIENTO");
    Establecimiento establecimiento = new Establecimiento("Hola", "SUCURSAL", new Municipio(6, "CABA"));
    ReporteDeIncidente reporteDeIncidente = new ReporteDeIncidente("CERRADO", Date.from(Instant.now()), Time.from(Instant.now()), miembroDeComunidad, entidad, establecimiento, servicio, "hola");
    mailJavax.recibirNotificacion(reporteDeIncidente, "juanpaoli@gmail.com");
  }
}
