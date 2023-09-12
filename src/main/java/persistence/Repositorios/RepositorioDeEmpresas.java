package persistence.Repositorios;

import Config.Config;
import domain.Entidades.OrganismoDeControl;
import lombok.Getter;
import services.APIs.Georef.ServicioGeoref;
import services.Archivos.CargadorDeDatos;
import services.Archivos.SistemaDeArchivos;

import java.util.List;

@Getter
public class RepositorioDeEmpresas {

    public RepositorioDeEmpresas() {}
    public static void main(String[] args) {

        List<OrganismoDeControl> empresas;

        CargadorDeDatos cargadorDeDatos = new CargadorDeDatos();
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        ServicioGeoref servicioGeoref = ServicioGeoref.instancia();

        RepositorioDeOrganismosDeControl repositorioDeOrganismos = new RepositorioDeOrganismosDeControl();
        empresas = cargadorDeDatos.cargaDeDatosMASIVA(sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV), servicioGeoref);
        empresas.forEach(e -> repositorioDeOrganismos.agregar(e));

    }
}


