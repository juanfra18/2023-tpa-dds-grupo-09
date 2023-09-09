package domain.Servicios;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@DiscriminatorValue("banio")
public class Banio extends Servicio{
    @Enumerated(EnumType.STRING)
    @Setter
    private TipoBanio tipoBanio;

    public Banio () {};
    public boolean estaActivo() {
        //TODO
        return true;
    }
    @Override
    public void setTipo(String tipo) {
        tipoBanio = TipoBanio.valueOf(tipo);
    }
    public String getTipo(){
        return "Banio " + String.valueOf(tipoBanio);
    }


    public boolean igualito(Servicio banio) {
        if (this == banio) {
            return true;
        }
        if (banio == null || getClass() != banio.getClass()) {
            return false;
        }
        Banio otro = (Banio) banio;
        return tipoBanio == otro.tipoBanio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoBanio);
    }
}

