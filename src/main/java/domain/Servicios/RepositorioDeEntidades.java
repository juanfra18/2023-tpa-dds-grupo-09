package domain.Servicios;

import com.opencsv.exceptions.CsvException;
import lombok.Getter;
import lombok.Setter;
import services.Archivos.SistemaDeArchivos;

import java.io.IOException;
import java.util.List;

@Getter
public class RepositorioDeEntidades {
    private List<Entidad> entidades;
    public RepositorioDeEntidades() throws IOException, CsvException {
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        this.entidades = sistemaDeArchivos.cargarDatosEntidades();
    }
}
