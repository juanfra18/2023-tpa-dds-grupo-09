package domain.Entidades;

import Config.Config;
import com.opencsv.exceptions.CsvException;
import lombok.Getter;
import services.Archivos.CargadorDeDatos;
import services.Archivos.SistemaDeArchivos;

import java.io.IOException;
import java.util.List;

@Getter
public class RepositorioDeEmpresas {
    private List<OrganismoDeControl> empresas;
    public RepositorioDeEmpresas(CargadorDeDatos cargadorDeDatos) throws IOException, CsvException {
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        this.empresas = cargadorDeDatos.cargaDeDatosMASIVA(sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV));
    }
}
