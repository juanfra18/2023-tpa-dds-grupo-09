package domain.Usuario;

import domain.Seguridad.InicioDeSesionException;
import domain.Seguridad.Seguridad;


public class Usuario {
    public String getUsername() {
        return username;
    }

    private String username;
    private String contrasenia;
    private String mail;
    private String apellido;
    private String nombre;

  public Usuario(Seguridad seguridad, String username, String contrasenia) throws InicioDeSesionException {
        this.username = username;
        this.contrasenia = contrasenia;
        seguridad.registrarUsuario(username,contrasenia);
    }
    public void cambiarContrasenia(Seguridad seguridad, String nuevaContrasenia) throws InicioDeSesionException {
      if(nuevaContrasenia != contrasenia)
      {
        seguridad.registrarUsuario(this.username,nuevaContrasenia);
        this.contrasenia = nuevaContrasenia;
      }
    }
}

