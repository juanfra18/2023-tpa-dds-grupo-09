document.querySelectorAll('.btn-nuevo-interes').forEach(button => {
    button.addEventListener('click', function() {
        var organismoId = this.getAttribute('data-organismo-id');
        var entidadPrestadoraId = this.getAttribute('data-entidadPrestadora-id');
        var entidadId = this.getAttribute('data-entidad-id');
        var establecimientoId = this.getAttribute('data-establecimiento-id');
        var servicioId = this.getAttribute('data-servicio-id');
        var rol = this.getAttribute('data-rol');

        fetch('/intereses/servicio/' + servicioId)
            .then(response => response.json())
            .then(interes => {
                if (!interes) {
                        fetch('/intereses/servicio/'+ servicioId +'/agregar/' + rol, {
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
                    window.alert("Ya es de tu interés este servicio");
                }
            });
    });
});
