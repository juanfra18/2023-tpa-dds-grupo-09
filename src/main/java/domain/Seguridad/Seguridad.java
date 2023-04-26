package domain.Seguridad;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Seguridad {
  private String mensajeDeError;
  private ValidadorDeContrasenias validador;
  public Seguridad(){
    validador = new ValidadorDeContrasenias();
  }
  public void registrarUsuario(String usuario, String contrasenia) throws InicioDeSesionException{
    mensajeDeError = validador.validarContrasenia(usuario, contrasenia);
    if (mensajeDeError != "") throw new InicioDeSesionException("\ncontrasenia invalida: " + mensajeDeError);
  }
}