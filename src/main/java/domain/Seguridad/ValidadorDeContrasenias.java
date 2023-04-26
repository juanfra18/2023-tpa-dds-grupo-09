package domain.Seguridad;

import Config.Config;

public class ValidadorDeContrasenias {

  private Integer longMin = 8;
  private String mensajeDeError = "";
  private VerificadorDeArchivos verificadorDeArchivos;

  public ValidadorDeContrasenias(){ //porque esta excepcion??
    verificadorDeArchivos = new VerificadorDeArchivos();
  }
  public String validarContrasenia(String username, String contrasenia){
    if (!this.longitudMinima(contrasenia)) mensajeDeError += "\nDebe contener al menos 8 caracteres";
    if (this.esFacil(contrasenia)) mensajeDeError += "\nDemasiado facil";
    if (this.contieneSecuenciasRepetidas(contrasenia) || this.contieneSubSecuenciasRepetidas(contrasenia)) mensajeDeError += "\nNo debe contener secuencia repetidas";
    if (this.perteneceADiccionario(contrasenia)) mensajeDeError += "\nNo debe pertenecer al diccionario";
    if (!this.alMenosUnaMayuscula(contrasenia)) mensajeDeError += "\nDebe contener al menos 1 mayuscula";
    if (!this.contieneSimbolos(contrasenia)) mensajeDeError += "\nDebe contener al menos 1 simbolo";
    if (!this.difiereNombreUsuario(username, contrasenia)) mensajeDeError += "\nDebe ser distinta al usuario";
    return mensajeDeError;
  }
  public boolean alMenosUnaMayuscula(String contrasenia) {
    for (int i = 0; i <= contrasenia.length() - 1; i++) {
      if (Character.isUpperCase(contrasenia.charAt(i))) {
        return true;
      }
    }
    return false;
  }
  public boolean contieneSimbolos(String contrasenia) {

    for (int i = 0; i < contrasenia.length(); i++) {
      if (verificadorDeArchivos.estaEnArchivo(contrasenia.substring(i, i+1), Config.archivoSimbolosRuta)) {
        return true;
      }
    }

    return false;
  }
  public boolean contieneSecuenciasRepetidas(String contrasenia) {
    for (int i = 1; i <= contrasenia.length() - 1; i++) {
      if (contrasenia.charAt(i) == contrasenia.charAt(i - 1)) {
        return true;
      }
      int a = contrasenia.charAt(i);
      int b = contrasenia.charAt(i - 1);
      if (a == b + 1) {
        return true;
      }
    }
    return false;
  }
  public boolean contieneSubSecuenciasRepetidas(String contrasenia) {

    int largo = contrasenia.length();
    for (int i = 1; i <= Math.floor(largo / 2); i++) {
      for (int j = 0; j <= largo - 2 * i; j++) {
        String subcad1 = contrasenia.substring(j, j + i);
        String subcad2 = contrasenia.substring(j + i, 2 * i + j);
        //System.out.println(subcad1 + " " + subcad2 + "\n");// PARA PROBAR ERRORES
        if ((subcad1).equals(subcad2)) {
          //System.out.println (subcad1+ " " +subcad2 + "\n");
          return true;
        }
      }
    }
    return false;
  }
  public boolean longitudMinima(String contrasenia) {
    return contrasenia.length() >= 8;
  }
  public boolean esFacil(String contrasenia)  {
    return verificadorDeArchivos.estaEnArchivo(contrasenia,Config.archivoContraseniasFacilesRuta );
  }
  public boolean perteneceADiccionario(String contrasenia)  {
    return verificadorDeArchivos.estaEnArchivo(contrasenia, Config.archivoDiccionarioRuta);
  }
  public boolean difiereNombreUsuario(String username, String contrasenia) {
    return contrasenia != username;
  }
}
