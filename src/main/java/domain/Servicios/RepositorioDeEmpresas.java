package domain.Servicios;

import Config.Config;
import com.opencsv.exceptions.CsvException;
import lombok.Getter;
import services.Archivos.SistemaDeArchivos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class RepositorioDeEmpresas {
    private List<Entidad> entidades;
    private List<OrganismoDeControl> organismos;
    public RepositorioDeEmpresas() throws IOException, CsvException {
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        this.entidades = this.cargarDatosEntidades(sistemaDeArchivos);
        this.organismos = this.cargarDatosOrganismos(sistemaDeArchivos);
    }
    public List<OrganismoDeControl> cargarDatosOrganismos(SistemaDeArchivos sistemaDeArchivos) throws IOException, CsvException {
        List<OrganismoDeControl> organismos = new ArrayList<>();
        List<String[]> lista = sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV_ORGANISMOS);

        for(String[] elemento: lista){
            organismos.add(new OrganismoDeControl(elemento[0]));
        }
        return organismos;
    }
    public List<Entidad> cargarDatosEntidades(SistemaDeArchivos sistemaDeArchivos) throws IOException, CsvException {
        List<Entidad> entidades = new ArrayList<>();
        List<String[]> lista = sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV_ENTIDADES);

        for (String[] elemento : lista) {
            Entidad entidad = new Entidad(elemento[0], elemento[1]);
            if (elemento.length != 2) {
                for (int i = 2; i < elemento.length; i++) {
                    String[] arreglo = elemento[i].split(",");
                    entidad.agregarEstablecimiento(new Establecimiento(arreglo[0], arreglo[1], arreglo[2]));
                }
            }
            entidades.add(entidad);
           }

        return entidades;
    }
}
