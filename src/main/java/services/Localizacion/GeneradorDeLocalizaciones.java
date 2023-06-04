package services.Localizacion;

public class GeneradorDeLocalizaciones {

  public GeneradorDeLocalizaciones() {
  }

  public Localizacion devolverLocalizacion(int idProvinciaSeleccionado, int idDepartamentoSeleccionado, int idMunicipioSeleccionado) {
    if (idProvinciaSeleccionado != 0 && idMunicipioSeleccionado != 0 && idDepartamentoSeleccionado != 0) {
      Municipio municipioSeleccionado = new Municipio(idProvinciaSeleccionado, idDepartamentoSeleccionado, idMunicipioSeleccionado);
      return municipioSeleccionado;
    } else if (idProvinciaSeleccionado != 0 && idDepartamentoSeleccionado != 0) {
      Departamento departamentoSeleccionado = new Departamento(idProvinciaSeleccionado, idDepartamentoSeleccionado);
      return departamentoSeleccionado;
    } else if (idProvinciaSeleccionado != 0) {
      Provincia provinciaSeleccionada = new Provincia(idProvinciaSeleccionado);
      return provinciaSeleccionada;
    }
    return null;
  }
}
