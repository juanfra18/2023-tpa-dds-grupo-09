package domain.Seguridad;

import Config.Config;
import domain.Seguridad.Reglas.*;

import java.util.ArrayList;
import java.util.List;

public class ValidadorDeContrasenias {
  private List<ReglaContrasenia> reglas;
  public ValidadorDeContrasenias(){
    reglas = new ArrayList<>();
    reglas.add(new AlMenosUnaMayuscula());
    reglas.add(new ContieneSimbolos());
    reglas.add(new NoContieneSecuenciasRepetidas());
    reglas.add(new LongitudMinima());
    reglas.add(new NoEstaEnArchivo(Config.ARCHIVO_CONTRASENIAS_COMUNES_RUTA));
    reglas.add(new NoEstaEnArchivo(Config.ARCHIVO_DICCIONARIO_RUTA));
  }
  public void validarContrasenia(String contrasenia){
    if (!verificaReglas(contrasenia)) {
      throw new RegistroDeUsuarioException("Contraseña inválida");
    };
  }

  public boolean verificaReglas(String contrasenia) {
    return reglas.stream().allMatch(regla -> regla.cumpleRegla(contrasenia));
  }

}
