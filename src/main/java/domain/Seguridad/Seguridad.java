package domain.Seguridad;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
@Setter
@Getter
public class Seguridad {
  private String mensajeDeError;
  private ValidadorDeContrasenias validador;
  public Seguridad() throws FileNotFoundException {
    validador = new ValidadorDeContrasenias();
  }
  public void registrarUsuario(String usuario, String contrasenia) throws InicioDeSesionException, IOException {
    mensajeDeError = validador.validarContrasenia(usuario, contrasenia);
    if (mensajeDeError != "") throw new InicioDeSesionException("\ncontrasenia invalida: " + mensajeDeError);
  }
}