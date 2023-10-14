package controllers;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import models.Config.Config;
import models.domain.Entidades.OrganismoDeControl;
import models.domain.Personas.MiembroDeComunidad;
import models.domain.Usuario.TipoRol;
import models.domain.Usuario.Usuario;
import models.persistence.EntityManagerSingleton;
import models.persistence.Repositorios.RepositorioDeOrganismosDeControl;
import models.services.APIs.Georef.ServicioGeoref;
import models.services.Archivos.CargadorDeDatos;
import models.services.Archivos.SistemaDeArchivos;
import server.utils.ICrudViewsHandler;

import javax.persistence.EntityManager;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpresasController extends ControllerGenerico implements ICrudViewsHandler {

  @Override
  public void index(Context context) {
  }

  @Override
  public void show(Context context) {

  }

  @Override
  public void create(Context context) {
  }

  @Override
  public void save(Context context) {
    EntityManager em = EntityManagerSingleton.getInstance();
    UploadedFile archivoCSV = context.uploadedFile("archivoCSV");
    CargadorDeDatos cargadorDeDatos = new CargadorDeDatos();
    SistemaDeArchivos sistemaDeArchivos = new SistemaDeArchivos();
    ServicioGeoref servicioGeoref = ServicioGeoref.instancia();
    RepositorioDeOrganismosDeControl repositorioDeOrganismosDeControl = RepositorioDeOrganismosDeControl.getInstancia();
    List<OrganismoDeControl> empresas;

    String rutaCSV= "resources/datos.csv";
    File archivo = new File(rutaCSV);

    try (InputStream input = archivoCSV.content();
         OutputStream output = new FileOutputStream(archivo)) {
      Files.copy(input, archivo.toPath(), StandardCopyOption.REPLACE_EXISTING);
      System.out.println("Archivo guardado con éxito.");
    } catch (IOException e) {
      e.printStackTrace();
    }



    try {
      em.getTransaction().begin();
      empresas = cargadorDeDatos.cargaDeDatosMASIVA(sistemaDeArchivos.csvALista(rutaCSV.toString()), servicioGeoref);
      empresas.forEach(e -> repositorioDeOrganismosDeControl.agregar(e));
      em.getTransaction().commit();
    } catch (Exception e) {
      em.getTransaction().rollback();
    } finally {
      em.close();
    }
    context.redirect("/cargarEmpresas");
  }

  @Override
  public void edit(Context context) {

  }

  @Override
  public void update(Context context) {
    EntityManager em = EntityManagerSingleton.getInstance();
    Map<String, Object> model = new HashMap<>();
    Usuario usuarioLogueado = super.usuarioLogueado(context,em);
    boolean usuarioBasico = false;
    boolean usuarioEmpresa = false;
    boolean administrador = false;

    MiembroDeComunidad miembroDeComunidad = this.miembroDelUsuario(usuarioLogueado.getId().toString());

    if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_BASICO)
    {
      usuarioBasico = true;
    }
    else if(usuarioLogueado.getRol().getTipo() == TipoRol.USUARIO_EMPRESA)
    {
      usuarioEmpresa = true;
    }
    else if(usuarioLogueado.getRol().getTipo() == TipoRol.ADMINISTRADOR)
    {
      administrador = true;
    }

    model.put("usuarioBasico",usuarioBasico);
    model.put("usuarioEmpresa",usuarioEmpresa);
    model.put("administrador",administrador);
    model.put("miembro_id",miembroDeComunidad.getId());
    context.render("CargaDeEntidadesYOrganismos.hbs", model);
    em.close();
  }

  @Override
  public void delete(Context context) {

  }
}
