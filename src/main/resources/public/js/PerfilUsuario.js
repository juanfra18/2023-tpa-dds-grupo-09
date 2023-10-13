document.getElementById('cerrarSesion').addEventListener('click', function() {
    // Realizar una solicitud al servidor para cerrar la sesión
    fetch('/cerrarSesion', {
        method: 'POST'
    })
    .then(response => {
        if (response.ok) {
            window.location.href = '/inicioDeSesion'; // Cambia a la URL que desees
        } else {
            // Hubo un error al cerrar la sesión
            console.error('Error al cerrar sesión');
        }
    });
});

