package domain.Servicios;


import com.opencsv.exceptions.CsvException;
import lombok.Getter;
import lombok.Setter;
import services.Archivos.SistemaDeArchivos;

import java.io.IOException;
import java.util.List;

@Getter
public class RepositorioDeOrganismos {
    private List<OrganismoDeControl> organismos;
    public RepositorioDeOrganismos() throws IOException, CsvException {
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        this.organismos = sistemaDeArchivos.cargarDatosOrganismos();
    }
}
