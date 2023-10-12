document.querySelectorAll('.btn-eliminar').forEach(button => {
    button.addEventListener('click', function() {
        var miembroId = this.getAttribute('data-miembro-id');

        // Realizar una solicitud al servidor para eliminar el usuario
        fetch('/administrarUsuarios/eliminarUsuario/' + miembroId, {
            method: 'POST'
        })
        .then(response => {
            if (response.ok) {
                // Usuario eliminado con éxito, puedes actualizar la interfaz o redirigir si es necesario
                console.log('Usuario eliminado con éxito');
            } else {
                console.error('Error al eliminar usuario');
            }
        });
    });
});
