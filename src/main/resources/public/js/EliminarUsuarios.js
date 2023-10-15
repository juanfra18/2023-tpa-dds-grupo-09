document.querySelectorAll('.btn-eliminar').forEach(button => {
    button.addEventListener('click', function() {
        var miembroId = this.getAttribute('data-miembro-id');
        var resultado = window.confirm('¿Estás seguro? Esta acción es irreversible');
        if (resultado === true) {
            fetch('/usuarios/eliminar/' + miembroId, {
                        method: 'POST'
                    })
                    .then(response => {
                        console.log(response);
                        if (response.ok) {
                            window.location.href = 'usuarios';
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
