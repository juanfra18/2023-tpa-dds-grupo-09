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


    public boolean igualito(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Banio otro = (Banio) obj;
        return tipoBanio == otro.tipoBanio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoBanio);
    }
}

/*
 public boolean equals(Object otroServicio) {
        if (otroServicio instanceof Banio) {
            return ((Banio) otroServicio).tipoBanio == this.tipoBanio;
        }
        else {
            return false;
        }
    }
 */
