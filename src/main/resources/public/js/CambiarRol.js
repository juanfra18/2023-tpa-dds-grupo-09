document.querySelectorAll('.btn-cambiar-rol').forEach(button => {
    button.addEventListener('click', function() {
        var servicioId = this.getAttribute('data-servicio-id');
                        fetch('/intereses/servicio/'+ servicioId +'/cambiarRol', {
                            method: 'POST'
                        })
                        .then(response => {
                            if (response.ok) {
                                window.location.href = '/intereses/servicios';
                            } else {
                                window.alert('Error al cambiar el rol');
                            }
                        });
            });
    });
