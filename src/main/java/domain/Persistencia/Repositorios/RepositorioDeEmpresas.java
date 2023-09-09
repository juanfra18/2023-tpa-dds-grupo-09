package domain.Persistencia.Repositorios;

import Config.Config;
import domain.Entidades.OrganismoDeControl;
import domain.Persistencia.Repositorios.RepositorioEntidad;
import lombok.Getter;
import services.APIs.Georef.AdapterServicioGeo;
import services.APIs.Georef.GeorefService;
import services.APIs.Georef.ServicioGeoref;
import services.Archivos.CargadorDeDatos;
import services.Archivos.SistemaDeArchivos;
import java.util.List;

@Getter
public class RepositorioDeEmpresas {
    private static List<OrganismoDeControl> empresas; //Rveisar si puedo evitar hacerlo static TODO
    public RepositorioDeEmpresas(CargadorDeDatos cargadorDeDatos, AdapterServicioGeo servicioGeo) {
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        this.empresas = cargadorDeDatos.cargaDeDatosMASIVA(sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV), servicioGeo);
    }
    public static void main(String[] args) {
        CargadorDeDatos cargadorDeDatos = new CargadorDeDatos();
        ServicioGeoref servicioGeoref = ServicioGeoref.instancia();

        RepositorioDeOrganismosDeControl repositorioDeOrganismos = new RepositorioDeOrganismosDeControl();
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        cargadorDeDatos.cargaDeDatosMASIVA(sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV), servicioGeoref);
        empresas.forEach(e -> repositorioDeOrganismos.agregar(e));
    }
}


