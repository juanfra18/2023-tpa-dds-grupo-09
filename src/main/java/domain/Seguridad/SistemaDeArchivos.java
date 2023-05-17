package domain.Seguridad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SistemaDeArchivos
{

  public boolean estaEnArchivo(String texto, String ruta) throws IOException {
    String linea = null;
    FileReader fr = null;
    BufferedReader br = null;

    boolean encontrado = false;


    File archivo = null;
    archivo = new File(ruta);
    fr = new FileReader(archivo);
    br = new BufferedReader(fr);
    while ((linea = br.readLine()) != null) {
      if (texto.equals(linea)) encontrado = true;
    }

    if (null != fr) {
      fr.close();
    }

    return encontrado;
  }

}

