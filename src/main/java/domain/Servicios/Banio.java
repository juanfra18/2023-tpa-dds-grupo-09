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
}
