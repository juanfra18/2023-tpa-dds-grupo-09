package domain.Servicios;

import lombok.Getter;

@Getter
public class Banio implements Servicio{
    private TipoBanio tipoBanio;
    public boolean estaActivo() {
        //TODO
        return true;
    }
    public String getTipo(){
        return String.valueOf(tipoBanio);
    }
    public Banio(String tipoBanio) {
        this.tipoBanio = TipoBanio.valueOf(tipoBanio);
    }

    public boolean equals(Object otroServicio) {
        if (otroServicio instanceof Banio) {
            return ((Banio) otroServicio).tipoBanio == this.tipoBanio;
        }
        else {
            return false;
        }
    }
}
