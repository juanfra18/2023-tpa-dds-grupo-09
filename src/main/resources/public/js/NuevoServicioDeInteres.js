document.querySelectorAll('.btn-nuevo-interes').forEach(button => {
    button.addEventListener('click', function() {
        var organismoId = this.getAttribute('data-organismo-id');
        var entidadPrestadoraId = this.getAttribute('data-entidadPrestadora-id');
        var entidadId = this.getAttribute('data-entidad-id');
        var establecimientoId = this.getAttribute('data-establecimiento-id');
        var servicioId = this.getAttribute('data-servicio-id');

        fetch('/intereses/servicio/' + servicioId)
            .then(response => response.json())
            .then(interes => {
                if (!interes) {
                    var rol = prompt("¿Cuál es tu rol? (OBSERVADOR/AFECTADO)");

                    if (rol === "OBSERVADOR" || rol === "AFECTADO") {
                        fetch('/intereses/servicio/agregar/' + servicioId + '/' + rol, {
                            method: 'POST'
                        })
                        .then(response => {
                            if (response.ok) {
                                window.location.href = '/organismosDeControl/' + organismoId + '/entidadesPrestadoras/' + entidadPrestadoraId + '/entidades/' + entidadId + '/establecimientos/' + establecimientoId + '/servicios';
                            } else {
                                window.alert('Error al definir nuevo interés');
                            }
                        });
                    } else {
                        window.alert("Por favor, selecciona un rol.");
                    }
                } else {
                    window.alert("Ya es de tu interés este servicio");
                }
            });
    });
});
