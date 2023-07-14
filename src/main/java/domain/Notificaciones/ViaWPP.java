package domain.Notificaciones;


public class ViaWPP implements MedioDeComunicacion{
  private AdapterViaWPP servicioWPP;
  private String destinatario;
  public ViaWPP(String destinatario){
    this.servicioWPP = new ViaWPPConcreto(); //acoplamiento
    this.destinatario = destinatario;
  }
  public void recibirNotificacion(String mensaje, String asunto) {
    this.servicioWPP.recibirNotificacion(mensaje, this.destinatario, asunto);
  }
}
