package persistence.Repositorios;

import domain.Entidades.Entidad;
import domain.Entidades.Establecimiento;
import domain.Entidades.TipoEntidad;
import domain.Entidades.TipoEstablecimiento;
import domain.Servicios.Banio;
import domain.Servicios.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import services.Localizacion.Municipio;
import services.Localizacion.Provincia;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioEntidad implements WithSimplePersistenceUnit {
    private EntityTransaction tx;
    public RepositorioEntidad(){
        this.tx = entityManager().getTransaction();
    }

    public static void main(String[] args) {
        RepositorioEntidad repo = new RepositorioEntidad();

        Provincia jujuy = new Provincia();
        jujuy.setId(38);
        jujuy.setNombre("Jujuy");
        Municipio Yavi = new Municipio();
        Yavi.setId(386273);
        Yavi.setNombre("Yavi");
        Yavi.setProvincia(jujuy);

        Servicio banio = new Banio();
        banio.setTipo("DAMAS");

        Establecimiento estacionYavi = new Establecimiento();
        estacionYavi.setLocalizacion(Yavi);
        estacionYavi.setNombre("Yavi");
        estacionYavi.setTipoEstablecimiento(TipoEstablecimiento.valueOf("ESTACION"));
        estacionYavi.agregarServicio(banio);

        Entidad ferrocarrilMitre = new Entidad();
        ferrocarrilMitre.setTipoEntidad(TipoEntidad.valueOf("FERROCARRIL"));
        ferrocarrilMitre.setNombre("Mitre");
        ferrocarrilMitre.agregarEstablecimiento(estacionYavi);


        System.out.println(repo.buscar(ferrocarrilMitre.getId()).getNombre());

        System.out.println(repo.buscarTodos().size());

    }
    public Entidad buscar(long id){
        return entityManager().find(Entidad.class, id);
    }
    public List<Entidad> buscarTodos(){
        return entityManager().createQuery("from Entidad", Entidad.class).getResultList();
    }
}
