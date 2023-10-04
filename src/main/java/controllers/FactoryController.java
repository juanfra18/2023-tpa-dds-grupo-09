package controllers;

public class FactoryController {

  public static Object controller(String nombre) {
    Object controller = null;
    switch (nombre) {
      case "rankings": controller = new RankingsController(); break;
      case "menuPrincipal": controller = new MenuPrincipalController(); break;
      case "perfil": controller = new PerfilController();break;
    }
    return controller;
  }
}
