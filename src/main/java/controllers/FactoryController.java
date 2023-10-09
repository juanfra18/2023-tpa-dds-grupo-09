package controllers;

public class FactoryController {

  public static Object controller(String nombre) {
    Object controller = null;
    switch (nombre) {
      case "rankings": controller = new RankingsController(); break;
      case "menuPrincipal": controller = new MenuPrincipalController(); break;
      case "perfil": controller = new UsuariosController();break;
      case "reporteDeIncidente": controller = new ReporteDeIncidenteController();break;
      case "incidentes": controller = new IncidentesController();break;
      case "empresas": controller = new EmpresasController();break;
      case "comunidades": controller = new ComunidadesController();break;
    }
    return controller;
  }
}
