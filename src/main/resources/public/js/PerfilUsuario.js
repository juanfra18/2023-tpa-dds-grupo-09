document.getElementById('cerrarSesion').addEventListener('click', function() {
    fetch('/cerrarSesion', {
        method: 'POST'
    })
    .then(response => {
        if (response.ok) {
            window.location.href = '/';
        } else {
            window.alert('Error al cerrar sesión');
        }
    });
});

