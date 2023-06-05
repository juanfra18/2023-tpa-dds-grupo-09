package domain.Entidades;

import Config.Config;
import com.opencsv.exceptions.CsvException;
import lombok.Getter;
import services.Archivos.SistemaDeArchivos;

import java.io.IOException;
import java.util.List;

@Getter
public class RepositorioDeEmpresas {
    private List<OrganismoDeControl> empresas;
    public RepositorioDeEmpresas() throws IOException, CsvException {
        this.empresas = CargadorDeDatos.cargaDeDatosMASIVA(SistemaDeArchivos.csvALista(Config.ARCHIVO_CSV));
    }
}
