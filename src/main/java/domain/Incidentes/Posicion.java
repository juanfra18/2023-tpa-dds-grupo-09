package domain.Incidentes;

import domain.Persistencia.Persistente;
import lombok.Getter;

import javax.persistence.*;


@Getter
@Entity
@Table(name = "posicion")
public class Posicion extends Persistente {
    @Column(name = "latitud")
    private double latitud;
    @Column(name = "longitud")
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
