package domain.Entidades;

import Config.Config;
import domain.Persistencia.Repositorios.RepositorioEntidad;
import lombok.Getter;
import services.APIs.Georef.AdapterServicioGeo;
import services.Archivos.CargadorDeDatos;
import services.Archivos.SistemaDeArchivos;
import java.util.List;

@Getter
public class RepositorioDeEmpresas {
    private List<OrganismoDeControl> empresas; //TODO
    public RepositorioDeEmpresas(CargadorDeDatos cargadorDeDatos, AdapterServicioGeo servicioGeo) {
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        this.empresas = cargadorDeDatos.cargaDeDatosMASIVA(sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV), servicioGeo);
    }
    public static void main(String[] args) {
        CargadorDeDatos cargadorDeDatos = new CargadorDeDatos();
        RepositorioDeOrganismos repositorioDeOrganismos = new RepositorioDeOrganismos();
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        cargadorDeDatos.cargaDeDatosMASIVA(sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV), se);
        this.empresas.forEach(e => repositorioDeOrganismos.agregar(e));
    }
}


