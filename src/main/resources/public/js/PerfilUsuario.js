document.getElementById('cerrarSesion').addEventListener('click', function() {
    console.log("CLICK CLICK CLICK\n");
    // Realizar una solicitud al servidor para cerrar la sesión
    fetch('/cerrarSesion', {
        method: 'POST'
    })
    .then(response => {
        if (response.ok) {
            // La sesión se cerró exitosamente, redirigir al usuario a la página de inicio de sesión
            window.location.href = '/inicioDeSesion'; // Cambia a la URL que desees
        } else {
            // Hubo un error al cerrar la sesión
            console.error('Error al cerrar sesión');
        }
    });
});
