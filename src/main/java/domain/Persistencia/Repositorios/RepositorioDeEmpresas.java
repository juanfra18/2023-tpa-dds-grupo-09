package domain.Persistencia.Repositorios;

import Config.Config;
import domain.Entidades.OrganismoDeControl;
import domain.Persistencia.Repositorios.RepositorioEntidad;
import domain.Servicios.Banio;
import domain.Servicios.Elevacion;
import domain.Servicios.TipoBanio;
import domain.Servicios.TipoElevacion;
import lombok.Getter;
import services.APIs.Georef.AdapterServicioGeo;
import services.APIs.Georef.GeorefService;
import services.APIs.Georef.ServicioGeoref;
import services.Archivos.CargadorDeDatos;
import services.Archivos.SistemaDeArchivos;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RepositorioDeEmpresas {

    public RepositorioDeEmpresas() {}
    public static void main(String[] args) {

        List<OrganismoDeControl> empresas;

        CargadorDeDatos cargadorDeDatos = new CargadorDeDatos();
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        ServicioGeoref servicioGeoref = ServicioGeoref.instancia();

        RepositorioServicio repositorioServicio = new RepositorioServicio();
        Banio banio1 = new Banio();
        Banio banio2 = new Banio();
        Banio banio3 = new Banio();
        banio1.setTipo(TipoBanio.DAMAS.toString());
        repositorioServicio.agregar(banio1);
        banio2.setTipo(TipoBanio.CABALLEROS.toString());
        repositorioServicio.agregar(banio2);
        banio3.setTipo(TipoBanio.UNISEX.toString());
        repositorioServicio.agregar(banio3);
        Elevacion elevacion1 = new Elevacion();
        Elevacion elevacion2 = new Elevacion();
        elevacion1.setTipo(TipoElevacion.ESCALERAS_MECANICAS.toString());
        repositorioServicio.agregar(elevacion1);
        elevacion2.setTipo(TipoElevacion.ASCENSOR.toString());
        repositorioServicio.agregar(elevacion2);


        RepositorioDeOrganismosDeControl repositorioDeOrganismos = new RepositorioDeOrganismosDeControl();
        empresas = cargadorDeDatos.cargaDeDatosMASIVA(sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV), servicioGeoref);
        empresas.forEach(e -> repositorioDeOrganismos.agregar(e));

    }
}


