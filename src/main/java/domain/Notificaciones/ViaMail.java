package domain.Notificaciones;


public class ViaMail implements MedioDeComunicacion{
  private AdapterViaMail servicioMail;
  private String destinatario;
  public ViaMail(String destinatario){
    this.servicioMail = new ViaMailJavax(); //acoplamiento con biblioteca externa
    this.destinatario = destinatario;
  }
  public void recibirNotificacion(String mensaje, String asunto) {
    this.servicioMail.recibirNotificacion(mensaje, this.destinatario, asunto);
  }

  public void recibirArchivos(String ruta, String asunto){
    this.servicioMail.enviarArchivo(ruta,this.destinatario,asunto);
  }
  
}