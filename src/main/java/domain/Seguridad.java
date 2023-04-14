package domain;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.io.*;


@Setter
@Getter
public class Seguridad {
  private Integer longMin = 8;
  private String linea;
  private Usuario usuario;
  private boolean noSegura = false;
  FileReader fr = null;
  BufferedReader br = null;


  public Seguridad() throws FileNotFoundException { //porque esta excepcion??
  }



  public boolean validarContrasenia(String contrasenia) throws IOException { //extends RuntimeException?
      return this.longitudMinima(contrasenia) && !this.esFacil(contrasenia) && !this.contieneSecuenciasRepetidas(contrasenia) && !this.perteneceADiccionario(contrasenia);

  }

  public boolean contieneSecuenciasRepetidas(String contrasenia) {
    for (int i = 1; i <= contrasenia.length()-1; i++) {
      if (contrasenia.charAt(i) == contrasenia.charAt(i-1)) {
        return true;
      }
      int a = contrasenia.charAt(i);
      int b = contrasenia.charAt(i-1);
      if (a == b+1) {
        return true;
      }
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

  private boolean estaEnArchivo(String contrasenia, String ruta) {
    try {
      File contraseniasArchivo = null;
      contraseniasArchivo = new File(ruta);
      ;
      fr = new FileReader(contraseniasArchivo);
      ;
      br = new BufferedReader(fr);
      ;
      while ((linea = br.readLine()) != null)
      {
        if (contrasenia.equals(linea)) noSegura = true;
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }finally{
      try{
        if( null != fr ){
          fr.close();
        }
      }catch (Exception e2){
        e2.printStackTrace();
      }

    }
    return noSegura;
  }
}