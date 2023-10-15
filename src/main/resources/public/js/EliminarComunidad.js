document.querySelectorAll('.btn-eliminar').forEach(button => {
    button.addEventListener('click', function() {
        var comunidadId = this.getAttribute('data-comunidad-id');
        var resultado = window.confirm('¿Estás seguro? Esta acción es irreversible');

        if (resultado === true) {
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
        }
        else {
         window.alert('Pensá antes de actuar');
        }
    });
});
