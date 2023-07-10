package services.Archivos;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import domain.Entidades.Entidad;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AdapterOpenCSV implements AdapterLectorCSV {
    @Override
    public List<String[]> leer(String ruta) {
        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader(ruta)).build();
            reader.skip(1); //no lee el encabezado
            List<String[]> lista = reader.readAll();

            return lista;
        }
        catch (IOException | CsvException e) {
            throw new NoSePudoLeerArchivoCSV("No se pudo leer el archivo CSV");
        }
    }

    @Override
    public void escribir(String ruta, String[] encabezado ,List<Entidad> entidades) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(ruta));

            writer.writeNext(encabezado);
            for(Entidad entidad : entidades)
            {
                writer.writeNext(new String[]{entidad.getNombre(),entidad.getTipoEntidad().toString(),entidad.getCantidadDeIncidentesPorSemana().toString()});
            }
            writer.close();
        }
        catch (IOException e) {
            throw new NoSePudoLeerArchivoCSV("No se pudo leer el archivo CSV");
        }
    }
}
