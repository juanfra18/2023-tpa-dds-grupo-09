package ServicioAPI.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
public class APIIncidente extends APIGenerica{
  private APIFechaHora horaApertura;
  private APIFechaHora horaCierre;
  private APIEstablecimiento establecimiento;
  private APIServicio servicio;

  public Long tiempoDeCierre(){
    return ChronoUnit.MINUTES.between(LocalDateTime.parse(this.horaApertura.getValor()), LocalDateTime.parse(this.horaCierre.getValor()));
  }
  public boolean cerrado(){
    return this.horaCierre != null;
  }
}
