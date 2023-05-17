package Servicios.Localizacion;

import java.util.List;

public class ListadoDeDepartamentos {
    public int cantidad;
    public int total;
    public Parametro parametros;
    public List<Departamento> departamentos;

    private class Parametro {
        public List<String> campos;
        public int max;
        public List<String> provincia;
    }
}
