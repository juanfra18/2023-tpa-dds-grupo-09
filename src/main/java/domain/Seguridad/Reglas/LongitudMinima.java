package domain.Seguridad.Reglas;

import Config.Config;

public class LongitudMinima implements ReglaContrasenia{
    public boolean cumpleRegla(String contrasenia) {
        return contrasenia.length() >= Config.LONGITUD_MINIMA;
    }
}
