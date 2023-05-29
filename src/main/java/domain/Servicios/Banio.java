package domain.Servicios;

public class Banio implements Servicio{
    private boolean activo;
    @Override
    public boolean estaActivo() {
        return activo;
    }
}
