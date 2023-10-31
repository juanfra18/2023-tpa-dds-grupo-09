document.getElementById('cerrarSesion').addEventListener('click', function() {
    fetch('/cerrarSesion', {
        method: 'POST'
    })
    .then(response => {
        if (response.ok) {
            window.location.href = '/inicioDeSesion';
        } else {
            console.error('Error al cerrar sesión');
        }
    });
});

