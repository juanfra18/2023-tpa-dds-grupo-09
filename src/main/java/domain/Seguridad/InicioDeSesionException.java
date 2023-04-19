package domain.Seguridad;

public class InicioDeSesionException extends Exception{ //por lo tanto hay que chequearla usando throws new por ejemplo
  public InicioDeSesionException(String mensaje) {
    super(mensaje);
  }
}
