package domain.Persistencia.Converters;

import domain.Notificaciones.MedioDeComunicacion;
import domain.Notificaciones.ViaMail;
import domain.Notificaciones.ViaWPP;

import javax.persistence.AttributeConverter;
import java.util.Objects;

public class MedioDeComunicacionAttributeConverter implements AttributeConverter<MedioDeComunicacion, String> {
  @Override
  public String convertToDatabaseColumn(MedioDeComunicacion medioDeComunicacion) {
    String nombreMedio = "";

    if (medioDeComunicacion == null)
      return null;

    switch (medioDeComunicacion.getClass().getName()) {
      case "ViaMail": nombreMedio = "mail"; break;
      case "ViaWPP": nombreMedio = "wpp"; break;
    }

    return nombreMedio;
  }

  @Override
  public MedioDeComunicacion convertToEntityAttribute(String s) {
    MedioDeComunicacion medio = null;

    if (s == null)
      medio = null;

    if (Objects.equals(s, "wpp"))
      medio = new ViaWPP();

    if (Objects.equals(s, "mail"))
      medio = new ViaMail();

    return medio;
  }
}
