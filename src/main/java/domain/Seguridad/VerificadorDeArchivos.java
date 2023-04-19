package domain.Seguridad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class VerificadorDeArchivos
{
  private String linea;
  FileReader fr = null;
  BufferedReader br = null;

  public boolean estaEnArchivo(String texto, String ruta) {
    boolean noSegura = false;

    try {
      File contraseniasArchivo = null;
      contraseniasArchivo = new File(ruta);
      ;
      fr = new FileReader(contraseniasArchivo);
      ;
      br = new BufferedReader(fr);
      ;
      while ((linea = br.readLine()) != null) {
        if (texto.equals(linea)) noSegura = true;
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

}
