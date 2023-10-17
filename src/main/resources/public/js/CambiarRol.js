document.querySelectorAll('.btn-cambiar-rol').forEach(button => {
    button.addEventListener('click', function() {
        var servicioId = this.getAttribute('data-servicio-id');
        var rol = prompt("¿Cuál es tu rol? (OBSERVADOR/AFECTADO)");

                    if (rol === "OBSERVADOR" || rol === "AFECTADO") {
                        fetch('/intereses/servicio/cambiarRol/' + servicioId + '/' + rol, {
                            method: 'POST'
                        })
                        .then(response => {
                            if (response.ok) {
                                window.location.href = '/intereses';
                                console.log('Rol cambiado con éxito');
                            } else {
                                console.error('Error al cambiar el rol');
                            }
                        });
                    } else {
                        window.alert("Por favor, selecciona un rol.");
                    }
            });
    });
