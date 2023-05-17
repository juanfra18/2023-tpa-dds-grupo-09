package Servicios.Archivos;

import Config.Config;
import com.opencsv.exceptions.CsvException;
import Servicios.Archivos.SistemaDeArchivos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class TestArchivo {
    @Test
    public void testLecturaCsv() throws IOException, CsvException {
        SistemaDeArchivos sis = new SistemaDeArchivos();
        List<String[]> lista = sis.csvALista(Config.ARCHIVO_CSV);

        Assertions.assertEquals(lista.get(1)[1], "hola");

    }
}