package domain.Incidentes;

import lombok.Getter;

@Getter
public class Posicion {
    private double latitud;
    private double longitud;
    public Posicion(String coordenadas) { //Ingresa en formato "40.7486,-73.9864"
        String[] aux = coordenadas.split(",");
        this.latitud = Double.valueOf(aux[0]);
        this.longitud = Double.valueOf(aux[1]);
    }
    public double distancia(Posicion posicion) {
        return Math.sqrt(Math.pow(this.latitud-posicion.getLatitud(), 2)+Math.pow(this.longitud-posicion.getLongitud(), 2));
    }
}
