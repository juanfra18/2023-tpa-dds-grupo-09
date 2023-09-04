package domain.Servicios;

import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@DiscriminatorValue("banio")
public class Banio extends Servicio{
    @Enumerated(EnumType.STRING)
    private TipoBanio tipoBanio;
    public boolean estaActivo() {
        //TODO
        return true;
    }
    public String getTipo(){
        return "Banio " + String.valueOf(tipoBanio);
    }
    public Banio(String tipoBanio) {
        this.tipoBanio = TipoBanio.valueOf(tipoBanio);
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
