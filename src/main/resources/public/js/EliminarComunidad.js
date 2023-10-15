document.querySelectorAll('.btn-eliminar').forEach(button => {
    button.addEventListener('click', function() {
        var comunidadId = this.getAttribute('data-comunidad-id');

        // Realizar una solicitud al servidor para eliminar el usuario
        fetch('/comunidades/eliminarComunidad/' + comunidadId, {
            method: 'POST'
        })
        .then(response => {
            if (response.ok) {
               window.location.href = '/comunidades'
                console.log('Comunidad eliminada con éxito');
            } else {
                console.error('Error al eliminar usuario');
            }
        });
    });
});
