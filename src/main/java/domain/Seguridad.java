package domain;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;



@Setter
@Getter
public class Seguridad {
  private Integer longMin = 8;
  private String mensajeDeError;
  private String linea;
  private boolean noSegura = false;
  private List<String> simbolos;
  FileReader fr = null;
  BufferedReader br = null;


  public Seguridad() throws FileNotFoundException { //porque esta excepcion??
    simbolos = new ArrayList<>();
    simbolos.add("_");
    simbolos.add("-");
    simbolos.add("!");
    simbolos.add("@");
    simbolos.add("#");
    simbolos.add("$");
    simbolos.add("%");
    simbolos.add("^");
    simbolos.add("&");
    simbolos.add("*");
  }

  public void registrarse(String usuario, String contrasenia) throws InicioDeSesionException, IOException {
    this.validarContrasenia(usuario, contrasenia);
    if (mensajeDeError != null) throw new InicioDeSesionException("\ncontrasenia invalida: " + mensajeDeError);
    //TODO no entiendo porque imprime un NULL despues de contrasenia invalida
  }

  public String validarContrasenia(String usuario, String contrasenia) throws IOException { //extends RuntimeException?
    if (!this.longitudMinima(contrasenia)) mensajeDeError += "\nDebe contener al menos 8 caracteres";
    if (this.esFacil(contrasenia)) mensajeDeError += "\nDemasiado facil";
    if (this.contieneSecuenciasRepetidas(contrasenia)) mensajeDeError += "\nNo debe contener secuencia repetidas";
    if (this.perteneceADiccionario(contrasenia)) mensajeDeError += "\nNo debe pertenecer al diccionario";
    if (!this.alMenosUnaMayuscula(contrasenia)) mensajeDeError += "\nDebe contener al menos 1 mayuscula";
    if (!this.contieneSimbolos(contrasenia)) mensajeDeError += "\nDebe contener al menos 1 simbolo";
    if (!this.difiereNombreUsuario(usuario, contrasenia)) mensajeDeError += "\nDebe ser distinta al usuario";
    return mensajeDeError;
  }

  public boolean alMenosUnaMayuscula(String contrasenia) {
    for (int i = 1; i <= contrasenia.length() - 1; i++) {
      if (Character.isUpperCase(contrasenia.charAt(i))) {
        return true;
      }
    }
    return false;
  }

  public boolean contieneSimbolos(String contrasenia) {
    return simbolos.stream().anyMatch(s -> contrasenia.contains(s));
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

  public boolean contieneSecuenciasRepetidas2(String contrasenia) {

    int largo = contrasenia.length();
    for (int i = 1; i <= Math.floor(largo / 2); i++) {
      for (int j = 0; j <= largo - 2 * i; j++) {
        String subcad1 = contrasenia.substring(j, j + i);
        String subcad2 = contrasenia.substring(j + i, 2 * i + j);
        System.out.println(subcad1 + " " + subcad2 + "\n");// PARA PROBAR ERRORES
        if ((subcad1).equals(subcad2)) {
          //System.out.println (subcad1+ " " +subcad2 + "\n");
          return true;
        }
      }
      System.out.println("----------------------------------------\n");
    }
    return false;
  }

  public boolean longitudMinima(String contrasenia) {
    return contrasenia.length() >= 8;
  }

  public boolean esFacil(String contrasenia) throws IOException {
    return estaEnArchivo(contrasenia, "10k-worst-passwords.txt");
  }

  public boolean perteneceADiccionario(String contrasenia) throws IOException {
    return estaEnArchivo(contrasenia, "0_palabras_todas.txt");
  }

  public boolean estaEnArchivo(String contrasenia, String ruta) {
    try {
      File contraseniasArchivo = null;
      contraseniasArchivo = new File(ruta);
      ;
      fr = new FileReader(contraseniasArchivo);
      ;
      br = new BufferedReader(fr);
      ;
      while ((linea = br.readLine()) != null) {
        if (contrasenia.equals(linea)) noSegura = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (null != fr) {
          fr.close();
        }
      } catch (Exception e2) {
        e2.printStackTrace();
      }

    }
    return noSegura;
  }

  public boolean difiereNombreUsuario(String usuario, String contrasenia) {
    return contrasenia != usuario;
  }
}