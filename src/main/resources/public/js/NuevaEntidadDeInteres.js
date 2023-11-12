document.querySelectorAll('.btn-nuevo-interes').forEach(button => {
    button.addEventListener('click', function() {
        var organismoId = this.getAttribute('data-organismo-id');
        var entidadPrestadoraId = this.getAttribute('data-entidadPrestadora-id');
        var entidadId = this.getAttribute('data-entidad-id');

        fetch('/intereses/entidad/' + entidadId)
            .then(response => response.json())
            .then(interes => {
                if (!interes) {
                    fetch('/intereses/entidad/'+ entidadId +'/agregar' , {
                        method: 'POST'
                    })
                    .then(response => {
                        if (response.ok) {
                            window.location.href = '/organismosDeControl/' + organismoId + '/entidadesPrestadoras/' + entidadPrestadoraId + '/entidades';
                        } else {
                            window.alert('Error al definir nuevo interés');
                        }
                    });
                } else {
                    window.alert("Ya es de tu interes esta entidad");
                }
            });
    });
});
