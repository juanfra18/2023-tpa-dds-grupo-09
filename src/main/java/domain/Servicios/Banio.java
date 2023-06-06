package domain.Servicios;

public class Banio implements Servicio{
    private TipoBanio tipoBanio;
    public boolean estaActivo() {
        //TODO
        return true;
    }
    public Banio(String tipoBanio) {
        this.tipoBanio = TipoBanio.valueOf(tipoBanio);
    }
}
