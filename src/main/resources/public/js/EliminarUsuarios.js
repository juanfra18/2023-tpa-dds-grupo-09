document.querySelectorAll('.btn-eliminar').forEach(button => {
    button.addEventListener('click', function() {
        var miembroId = this.getAttribute('data-miembro-id');
        // Realizar una solicitud al servidor para eliminar el usuario
        var resultado = window.confirm('¿Estás seguro? Esta acción es irreversible');
        if (resultado === true) {
            fetch('/usuarios/eliminar/' + miembroId, {
                        method: 'POST'
                    })
                    .then(response => {
                        console.log(response);
                        if (response.ok) {
                            // Usuario eliminado con éxito, puedes actualizar la interfaz o redirigir si es necesario
                            console.log('Usuario eliminado con éxito');
                        } else {
                            console.error('Error al eliminar usuario');
                        }
                    });
        } else {
            window.alert('Pensá antes de actuar');
        }
    });
});
