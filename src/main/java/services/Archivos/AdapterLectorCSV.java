package services.Archivos;

import domain.Entidades.Entidad;

import java.util.List;

public interface AdapterLectorCSV {
    public List<String[]> leer(String ruta);
    public void escribir(String ruta, String[] encabezado ,List<String[]> lineas);
}
