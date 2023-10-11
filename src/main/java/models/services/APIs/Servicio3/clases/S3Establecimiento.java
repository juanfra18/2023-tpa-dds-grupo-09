package models.services.APIs.Servicio3.clases;


import lombok.Getter;
import lombok.Setter;
import models.domain.Entidades.Establecimiento;

@Setter
@Getter
public class S3Establecimiento extends S3Generica {
    public S3Establecimiento(Establecimiento establecimiento) {
        super(establecimiento);
    }
}
