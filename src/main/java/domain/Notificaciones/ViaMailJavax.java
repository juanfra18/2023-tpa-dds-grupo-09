package domain.Notificaciones;

import domain.Incidentes.ReporteDeIncidente;
import domain.Personas.MiembroDeComunidad;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ViaMailJavax implements AdapterViaMail{
  private String remitente;
  private String claveemail;
  private String destinatario;
  private String asunto;
  private String cuerpo;

  public void recibirNotificacion(ReporteDeIncidente reporteDeIncidente, MiembroDeComunidad destinatario){
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

    Session session = Session.getDefaultInstance(props);
    MimeMessage message = new MimeMessage(session);

    try {
      message.setFrom(new InternetAddress(remitente));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario.getMail()));   //Se podrían añadir varios de la misma manera
      message.setSubject(asunto);
      message.setText(cuerpo);
      Transport transport = session.getTransport("smtp");
      transport.connect("smtp.gmail.com", remitente, claveemail);
      transport.sendMessage(message, message.getAllRecipients());
      transport.close();
    }
    catch (MessagingException me) {
      me.printStackTrace();   //Si se produce un error
    }
  }
}
