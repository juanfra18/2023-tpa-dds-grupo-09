package services.Archivos;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.List;

public interface AdapterLectorCSV {
    public List<String[]> leer(String ruta) throws IOException, CsvException;
}
