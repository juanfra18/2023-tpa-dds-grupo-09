package domain.Entidades;

import Config.Config;
import lombok.Getter;
import services.APIs.Georef.AdapterServicioGeo;
import services.APIs.Georef.ServicioGeoref;
import services.Archivos.CargadorDeDatos;
import services.Archivos.SistemaDeArchivos;
import java.util.List;

@Getter
public class RepositorioDeEmpresas {
    private List<OrganismoDeControl> empresas;
    public RepositorioDeEmpresas(CargadorDeDatos cargadorDeDatos, AdapterServicioGeo servicioGeo) {
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        this.empresas = cargadorDeDatos.cargaDeDatosMASIVA(sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV), servicioGeo);
    }
}
