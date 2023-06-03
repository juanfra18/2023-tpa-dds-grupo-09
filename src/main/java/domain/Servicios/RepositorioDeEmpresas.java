package domain.Servicios;

import Config.Config;
import com.opencsv.exceptions.CsvException;
import lombok.Getter;
import services.Archivos.SistemaDeArchivos;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class RepositorioDeEmpresas {
    private List<OrganismoDeControl> empresas; //nombre provisorio
    //private List<EntidadPrestadora> entidades;
    //private List<OrganismoDeControl> organismos;
    public RepositorioDeEmpresas() throws IOException, CsvException {
        SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
        CargadorDeDatos cargador = new CargadorDeDatos();
        this.empresas = cargador.cargaDeDatosMASIVA(sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV_ENTIDADES));
        //this.entidades = this.cargarDatosEntidades(sistemaDeArchivos);
        //this.organismos = this.cargarDatosOrganismos(sistemaDeArchivos);
    }

    /*public void cargaDeDatosMASIVA(SistemaDeArchivos sistemaDeArchivos) throws IOException, CsvException
    {
        OrganismoDeControl organismoDeControl;
        EntidadPrestadora entidadPrestadora;
        Entidad entidad;
        Establecimiento establecimiento;

        List<String[]> lista = sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV_ORGANISMOS);
        for(String[] elemento: lista) {
            establecimiento = new Establecimiento(elemento[4], elemento[5]);
            entidad = new Entidad(elemento[2], elemento[3]);
            entidadPrestadora = new EntidadPrestadora(elemento[1]);
            organismoDeControl = new OrganismoDeControl(elemento[0]);

            if (!empresas.stream().anyMatch(organismoDeControl1 -> organismoDeControl1.getNombre() == elemento[0])){
                entidad.agregarEstablecimiento(establecimiento);
                entidadPrestadora.agregarEntidad(entidad);
                organismoDeControl.agregarEntidadPrestadora(entidadPrestadora);
                empresas.add(organismoDeControl);
            }
            else{
                int refOrganismo = -1;
                for(int indice = 0;indice<empresas.size();indice++)
                {
                    if(empresas.get(indice).getNombre() == elemento[0])
                    {
                        refOrganismo = indice;
                    }
                }
                if(!empresas.get(refOrganismo).getEntidadesPrestadoras().stream().anyMatch(entidadPrestadora1 -> entidadPrestadora1.getNombre() == elemento[1])) {
                    entidad.agregarEstablecimiento(establecimiento);
                    entidadPrestadora.agregarEntidad(entidad);
                    empresas.get(refOrganismo).agregarEntidadPrestadora(entidadPrestadora);
                }
                else {

                    int refEntidadPrestadora = -1;
                    for (int indice = 0; indice < empresas.get(refOrganismo).getEntidadesPrestadoras().size(); indice++) {
                        if (empresas.get(refOrganismo).getEntidadesPrestadoras().get(refEntidadPrestadora).getNombre() == elemento[0]) {
                            refEntidadPrestadora = indice;
                        }
                    }

                    if (!empresas.get(refOrganismo).getEntidadesPrestadoras().get(refEntidadPrestadora).
                        getEntidades().stream().anyMatch(entidad1 -> entidad1.getNombre() == elemento[2])) {
                        entidad.agregarEstablecimiento(establecimiento);
                        entidadPrestadora.agregarEntidad(entidad);
                    }
                    else {

                        int refEntidad = -1;
                        for (int indice = 0; indice < empresas.get(refOrganismo).getEntidadesPrestadoras().get(refEntidadPrestadora).getEntidades().size(); indice++) {
                            if (empresas.get(refOrganismo).getEntidadesPrestadoras().get(refEntidadPrestadora).getEntidades().get(refEntidad).getNombre() == elemento[0]) {
                                refEntidadPrestadora = indice;
                            }
                        }
                        if (!empresas.get(refOrganismo).getEntidadesPrestadoras().get(refEntidadPrestadora).getEntidades().get(refEntidad).
                            getEstablecimientos().stream().anyMatch(establecimiento1 -> establecimiento1.getNombre() == elemento[4])) {
                            entidad.agregarEstablecimiento(establecimiento);
                        }
                    }
                }
            }
        }
    }*/

    /*public List<OrganismoDeControl> cargarDatosOrganismos(SistemaDeArchivos sistemaDeArchivos) throws IOException, CsvException {
        List<OrganismoDeControl> organismos = new ArrayList<>();
        List<String[]> lista = sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV_ORGANISMOS);

        for(String[] elemento: lista){
            organismos.add(new OrganismoDeControl(elemento[0]));
        }
        return organismos;
    }
    public List<EntidadPrestadora> cargarDatosEntidades(SistemaDeArchivos sistemaDeArchivos) throws IOException, CsvException {
        List<EntidadPrestadora> entidades = new ArrayList<>();
        List<String[]> lista = sistemaDeArchivos.csvALista(Config.ARCHIVO_CSV_ENTIDADES);

        for (String[] elemento : lista) {
            EntidadPrestadora entidad = new EntidadPrestadora(elemento[0], elemento[1]);
            if (elemento.length != 2) {
                for (int i = 2; i < elemento.length; i++) {
                    String[] arreglo = elemento[i].split(",");
                    entidad.agregarEntidad(new Entidad(arreglo[0], arreglo[1], arreglo[2]));
                }
            }
            entidades.add(entidad);
           }

        return entidades;
    }*/
}
