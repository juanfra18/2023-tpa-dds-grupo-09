document.querySelectorAll('.btn-nuevo-interes').forEach(button => {
    button.addEventListener('click', function() {
        var organismoId = this.getAttribute('data-organismo-id');
        var entidadPrestadoraId = this.getAttribute('data-entidadPrestadora-id');
        var entidadId = this.getAttribute('data-entidad-id');

        fetch('/intereses/entidad/' + entidadId)
            .then(response => response.json())
            .then(interes => {
                if (!interes) {
                    fetch('/intereses/entidad/agregar/' + entidadId, {
                        method: 'POST'
                    })
                    .then(response => {
                        if (response.ok) {
                            window.location.href = '/organismosDeControl/' + organismoId + '/entidadesPrestadoras/' + entidadPrestadoraId + '/entidades';
                            console.log('Nuevo interés con éxito');
                        } else {
                            console.error('Error al definir nuevo interés');
                        }
                    });
                } else {
                    window.alert("Ya es de tu interes esta entidad");
                }
            });
    });
});
