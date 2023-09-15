package persistence.Repositorios;


import domain.Incidentes.ReporteDeIncidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Getter;

import javax.persistence.EntityTransaction;
import java.util.List;

@Getter
public class RepositorioDeReportesDeIncidentes extends RepositorioGenerico<ReporteDeIncidente> {

    private static RepositorioDeReportesDeIncidentes instancia = null;
    @Getter
    private RepositorioDeIncidentes repositorioDeIncidentes = RepositorioDeIncidentes.getInstancia();


    private RepositorioDeReportesDeIncidentes() {
        super(ReporteDeIncidente.class);
    }
    public static  RepositorioDeReportesDeIncidentes getInstancia() {
        if (instancia == null) {
            instancia = new RepositorioDeReportesDeIncidentes();
        }
        return instancia;
    }
}