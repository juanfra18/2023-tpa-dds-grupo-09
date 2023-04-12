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
  private String contrasenia;
  private boolean facil = false;
  File contraseniasFaciles = null;
  FileReader fr = null;
  BufferedReader br = null;


  public Seguridad() throws FileNotFoundException { //porque esta excepcion??
  }



  public boolean validarContrasenia(String contrasenia) throws IOException { //extends RuntimeException?
      return this.longitudMinima(contrasenia) && !this.esFacil(contrasenia);

  }

  public boolean contieneSecuenciasRepetidas(String contrasenia) {
    //TODO
    return true;
  }
  public boolean longitudMinima(String contrasenia) {
    return contrasenia.length() > 8;
  }
  public boolean esFacil(String contrasenia) throws IOException {
    try {
      contraseniasFaciles = new File("10k-worst-passwords.txt");
      ;
      fr = new FileReader(contraseniasFaciles);
      ;
      br = new BufferedReader(fr);
      ;
      while ((linea = br.readLine()) != null)
      {
        if (contrasenia.equals(linea)) facil = true;
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
    return facil;
  }

  public boolean noPerteneceDiccionario(String contrasenia) {
    //TODO
    return true;
  }
}
